package com.simorghsoftech.core.repositories;

import com.simorghsoftech.core.entities.BlockEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.List;

@ApplicationScoped
@Named("DbBlockRepository")
public class BlockEntityRepository implements PanacheRepository<BlockEntity> {

    public List<BlockEntity> findExistingBlocksInRange(long start, long end) {
        return getEntityManager()
                .createQuery(
                        "select b from BlockEntity b where b.number between :start and :end",
                        BlockEntity.class
                )
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public Long findLatestBlockNumber() {
        return getEntityManager()
                .createQuery(
                        "select max(b.number) from BlockEntity b",
                        Long.class
                )
                .getSingleResult();
    }
}
