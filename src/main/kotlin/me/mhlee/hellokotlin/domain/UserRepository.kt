package me.mhlee.hellokotlin.domain

import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun findByUserId(userId: String): User
}