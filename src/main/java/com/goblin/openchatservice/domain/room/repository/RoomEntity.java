package com.goblin.openchatservice.domain.room.repository;

import com.goblin.openchatservice.domain.member.repository.MemberEntity;
import com.goblin.openchatservice.domain.room.model.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int maxMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private MemberEntity owner;

    public static RoomEntity from(Room room) {
        return builder()
                .id(room.id())
                .name(room.name())
                .description(room.description())
                .maxMember(room.maxMember())
                .owner(MemberEntity.from(room.owner()))
                .build();
    }

    public Room toModel() {
        return new Room(id, name, description, maxMember, owner.toModel());
    }
}
