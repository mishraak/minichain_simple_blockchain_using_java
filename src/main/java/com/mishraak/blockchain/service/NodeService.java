/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardobork.blockchain.service;

import com.leonardobork.blockchain.domain.Block;
import com.leonardobork.blockchain.exception.InvalidBlockchainException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author leonardobork
 */
@Service
public class NodeService {

    @Autowired BlockService blockService;
    @Autowired LogService logService;
    @Autowired NetworkService networkService;
    
    public void mineBlock(String blockData){
        Block newBlock = blockService.generateNextBlock(blockData);
        blockService.addBlock(newBlock);
        this.logService.write("New block added, index number: " + newBlock.getIndex());
        this.networkService.broadcast(this.blockService.getBlockchain());
    }
    
    public void handleBlockchain(List<Block> blockchain) throws Exception{
        Block latestBlockReceived = blockchain.get(blockchain.size() - 1);
        Block latestBlockHeld = this.blockService.getLatestBlock();
        if(latestBlockReceived.getIndex() > latestBlockHeld.getIndex()){
            this.logService.write("Blockchain possibly behind. We have " + latestBlockHeld.getIndex() + ", peer has "+ latestBlockReceived.getIndex());
            if(latestBlockReceived.getHash().equals(latestBlockHeld.getPreviousHash())){
                this.logService.write("Blockchain is possible to append.");
                this.blockService.addBlock(latestBlockReceived);
                this.networkService.broadcast(this.blockService.getBlockchain());
            } else {
                this.blockService.replaceChain((ArrayList<Block>) blockchain);
            }
        } else {
            this.logService.write("Received blockchain is not longer than current blockchain. Do nothing");
            throw new InvalidBlockchainException("Received blockchain is not longer than current blockchain. Do nothing");
        }
    }
}
