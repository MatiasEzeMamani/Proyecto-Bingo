package com.matias.bingo.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "bingo_cards")
@NoArgsConstructor
public class BingoCard {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private int cardNumber;
	
	private int penaltyTurns = 0;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;
	
	@ElementCollection
    @CollectionTable(name = "card_numbers", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "number")
    @OrderColumn(name = "position")
    private List<Integer> numbers;
	
	@ElementCollection
	@CollectionTable(name = "card_marked_numbers", joinColumns = @JoinColumn(name = "card_id"))
	@Column(name = "marked_number")
	private List<Integer> markedNumbers = new ArrayList<>();
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
