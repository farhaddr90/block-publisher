package com.simorghsoftech.core.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "block")
public class BlockEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, nullable = false)
    public int number;

    @Column(unique = true, nullable = false)
    public String hash;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "raw_data")
    public byte[] rawData;
}
