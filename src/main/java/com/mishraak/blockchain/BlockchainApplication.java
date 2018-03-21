package com.leonardobork.blockchain;

import com.leonardobork.blockchain.service.BlockService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class BlockchainApplication {
        
	public static void main(String[] args) {
		SpringApplication.run(BlockchainApplication.class, args);
                BlockService.generateGenesisBlock();
	}
}
