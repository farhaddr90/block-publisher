package com.simorghsoftech.core.jobs;

import com.simorghsoftech.core.services.BlockService;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class ScannerJob {

    private final BlockService blockService;
    private final int batchSize;

    ScannerJob(
            final BlockService blockService,
            @ConfigProperty(name = "app.scanner.block.batch.size") final int batchSize
    ) {
        this.blockService = blockService;
        this.batchSize = batchSize;
    }

    @Scheduled(every = "${app.scanner.block.job.every.expr}", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    void dispatchTransactions() {
        System.out.println("Starting scanner job");
        int start = blockService.latestScannedBlock() + 1;
        int end = start + batchSize;
        blockService.storeFromBlockchain(start, end);
    }
}
