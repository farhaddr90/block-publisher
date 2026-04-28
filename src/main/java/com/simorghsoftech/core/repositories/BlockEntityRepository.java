package com.simorghsoftech.core.repositories;

import com.simorghsoftech.core.entities.BlockEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.List;

@ApplicationScoped
@Named("DbBlockRepository")
public class BlockEntityRepository implements PanacheRepository<BlockEntity> {

    public List<Integer> findExistingNumbersInRange(int start, int end) {
        return getEntityManager()
                .createQuery(
                        "select b.number from BlockEntity b where b.number between :start and :end",
                        Integer.class
                )
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public List<BlockEntity> findExistingBlocksInRange(int start, int end) {
        return getEntityManager()
                .createQuery(
                        "select b from BlockEntity b where b.number between :start and :end",
                        BlockEntity.class
                )
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public Integer findLatestBlockNumber() {
        return getEntityManager()
                .createQuery(
                        "select max(b.number) from BlockEntity b",
                        Integer.class
                )
                .getSingleResult();
    }
}
