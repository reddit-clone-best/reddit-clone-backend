package com.creddit.credditmainserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
public class Follower {

    @Id
    @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    private Long following; //memberId

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member follower;

    public Follower(){

    }

    public Follower(Member follower, Long following){
        this.following = following;
        this.follower = follower;
    }
}
