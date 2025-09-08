package com.mafe.cadastroempresarelacional.domain;

import com.mafe.cadastroempresarelacional.domain.enums.CompanySituation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Table(name = "companies")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cnpj;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "trade_name")
    private String tradeName;

    private String phone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "foundation_data")
    private LocalDate foundationData;

    @Column(name = "company_situation")
    @Enumerated(EnumType.STRING)
    private CompanySituation companySituation;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Adress> adresses;
}
