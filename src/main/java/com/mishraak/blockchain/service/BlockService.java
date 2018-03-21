/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardobork.blockchain.service;

import com.google.common.hash.Hashing;
import com.leonardobork.blockchain.domain.Block;
import com.leonardobork.blockchain.exception.InvalidBlockchainException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author leonardobork
 */
@Service
public class BlockService {

    @Autowired LogService logService;
    
    private static List<Block> BLOCKCHAIN = new ArrayList<Block>();
    
    public List<Block> getBlockchain(){
        return BLOCKCHAIN;
    }
    
    public void addBlock(Block newBlock){
        BLOCKCHAIN.add(newBlock);
    }

    public Block getLatestBlock() {
        return BLOCKCHAIN.get(BLOCKCHAIN.size() - 1);
    }

    public Block generateNextBlock(String blockData) {
        Block previousBlock = this.getLatestBlock();
        Integer nextIndex = previousBlock.getIndex() + 1;
        LocalDateTime nextDateTime = LocalDateTime.now();
        String nextHash = this.calculateHash(nextIndex, previousBlock.getHash(), nextDateTime.toString(), blockData);
        return new Block(nextIndex, previousBlock.getPreviousHash(), nextDateTime, blockData, nextHash);
    }

    public static void generateGenesisBlock() {
         BLOCKCHAIN.add(new Block(0, "0", LocalDateTime.now(), "my genesis block!!", "816534932c2b7154836da6afc367695e6337db8a921823784c14378abed4f7d7"));
    }

    public String calculateHash(Integer index, String previousHash, String timestamp, String data) {
        return Hashing.sha256()
                .hashString(index.toString() + previousHash + timestamp + data, StandardCharsets.UTF_8)
                .toString();
    }

    public String calculateHashForBlock(Block block) {
        return this.calculateHash(block.getIndex(), block.getPreviousHash(), block.getTimestamp().toString(), block.getData());
    }

    public boolean blockIsValid(Block newBlock, Block previousBlock) {
        if (previousBlock.getIndex() + 1 != newBlock.getIndex()) {
            this.logService.write("Invalid index.");
            return false;
        } else if (previousBlock.getHash().equals(newBlock.getPreviousHash())) {
            this.logService.write("Invalid previousHash.");
            return false;
        } else if (this.calculateHashForBlock(newBlock).equals(newBlock.getHash())) {
            this.logService.write("Invalid hash: " + this.calculateHashForBlock(newBlock) + " --- " + newBlock.getHash());
            return false;
        }

        return true;
    }

    public boolean isValidChain(ArrayList<Block> receivedBlockchain) {
        if (!(receivedBlockchain.get(0).getData().equals(BLOCKCHAIN.get(0).getData()) && receivedBlockchain.get(0).getHash().equals(BLOCKCHAIN.get(0).getHash()))) {
            return false;
        }
        ArrayList<Block> tempBlockchain = new ArrayList<Block>();
        tempBlockchain.add(receivedBlockchain.get(0));
        for (int i = 1; i < receivedBlockchain.size(); i++) {
            if (this.blockIsValid(receivedBlockchain.get(i), tempBlockchain.get(i - 1))) {
                tempBlockchain.add(receivedBlockchain.get(i));
            } else {
                return false;
            }
        }

        return true;
    }

//    There should always be only one explicit set of blocks in the chain at a given time.
//    In case of conflicts (e.g. two nodes both generate block number 72) we choose the chain that has the longest number of blocks.
    public void replaceChain(ArrayList<Block> newBlockchain) throws InvalidBlockchainException {
        if (isValidChain(newBlockchain) && newBlockchain.size() > BLOCKCHAIN.size()) {
            this.logService.write("Received blockchain is valid, updating current blockchain. \n Last index is: "
                    + newBlockchain.get(newBlockchain.size() - 1).getIndex() + "\n");
            BLOCKCHAIN = newBlockchain;
        } else {
            this.logService.write("Received blockchain is invalid, will not replace.");
            throw new InvalidBlockchainException("Received blockchain is invalid, will not replace.");
        }
    }
}
