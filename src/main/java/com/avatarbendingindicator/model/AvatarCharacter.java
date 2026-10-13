package com.avatarbendingindicator.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "characters")
public class AvatarCharacter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String summary;

    @Column(length = 2000)
    private String biography;

    @Enumerated(EnumType.STRING)
    @Column(name = "character_type", nullable = false, length = 50)
    private CharacterType characterType;

    @Column(nullable = false)
    private boolean avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_id")
    private Nation nation;

    @ManyToMany
    @JoinTable(
            name = "character_bending_styles",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "bending_style_id")
    )
    private Set<BendingStyle> bendingStyles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "character_affiliations",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "affiliation_id")
    )
    private Set<Affiliation> affiliations = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "character_aliases", joinColumns = @JoinColumn(name = "character_id"))
    @Column(name = "alias", nullable = false, length = 150)
    private Set<String> aliases = new HashSet<>();
}
