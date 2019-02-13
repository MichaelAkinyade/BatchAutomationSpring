package com.globalcapital.pack.database.entity;

import javax.persistence.*;

@Entity
public class Player {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@SequenceGenerator(name = "player_Sequence", sequenceName = "PLAYER_SEQ")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "num")
	private int num;

	@Column(name = "position")
	private String position;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
	
	public Player() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	
}