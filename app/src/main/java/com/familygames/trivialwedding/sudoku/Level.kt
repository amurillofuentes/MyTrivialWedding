package com.familygames.trivialwedding.sudoku

enum class Level(val numberOfProvidedDigits: Int) {
    FULL(70),
    AMATEUR(60),
    MIDDLE(45),
    PROFESSIONAL(30),
    EXPERT(20);
}