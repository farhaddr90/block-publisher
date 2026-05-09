package com.simorghsoftech.core.jobs;

import com.simorghsoftech.core.entities.BlockEntity;
import com.simorghsoftech.core.services.BlockService;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.Optional;

public class Job {


    private final BlockService blockService;
    private final int batchSize;
    private volatile Long latestReceivedBlock;

    Job(
            final BlockService blockService,
            @ConfigProperty(name = "app.scanner.block.batch.size") final int batchSize
    ) {
        this.blockService = blockService;
        this.batchSize = batchSize;
    }

    @PostConstruct
    void start() {
//        latestReceivedBlock =
//                blockService.latestScannedBlockNumber() == null
//                        ? blockService.latestBlockNumberFromBlockchain()
//                        : blockService.latestScannedBlockNumber();
        latestReceivedBlock =
                Optional.ofNullable(blockService.latestScannedBlockNumber())
                        .orElse(blockService.latestBlockNumberFromBlockchain());
    }

    @Scheduled(
            every = "${app.scanner.block.job.every.expr}",
            concurrentExecution = Scheduled.ConcurrentExecution.SKIP
    )
    synchronized void run() {

        long start = latestReceivedBlock + 1;
        long end = start + batchSize - 1;

        try {

            List<BlockEntity> entities =
                    blockService.storeFromBlockchain(start, end);

            long actualEnd = entities.stream()
                    .mapToLong(b -> b.number)
                    .max()
                    .orElse(start - 1);

            Log.info("Scanning " + start + " to " + actualEnd);

            latestReceivedBlock = actualEnd;

        } catch (Exception e) {
            Log.error("Scanner failed", e);
        }
    }

}
