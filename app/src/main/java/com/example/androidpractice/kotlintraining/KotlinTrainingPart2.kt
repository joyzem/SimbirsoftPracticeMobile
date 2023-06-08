package com.example.androidpractice.kotlintraining

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/*
    Создать enum Type с константами DEMO и FULL.
 */
enum class Type {
    DEMO,
    FULL
}


/*
    Реализовать класс данных User с полями id, name, age и type.
    У класса User создать ленивое свойство startTime, в котором получаем текущее время.
 */
data class User(
    val id: String,
    val name: String,
    val age: Int,
    val type: Type
) {
    val startType by lazy {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
}


/*
    Создать объект класса User, вывести в лог startTime данного юзера,
    после вызвать Thread.sleep(1000) и повторно вывести в лог startTime.
 */
fun createUser() {
    val user = User("id", "John", 11, Type.DEMO)
    println(user.startType)
    Thread.sleep(1000)
    println(user.startType)
}


/*
    Создать список пользователей, содержащий в себе один объект класса User.
    Используя функцию apply, добавить ещё несколько объектов класса User в список пользователей.
 */
fun createUsers(): List<User> {
    val users = mutableListOf(
        User("1", "John", 17, Type.DEMO)
    ).apply {
        val users = listOf(
            User("2", "David", 18, Type.FULL),
            User("3", "Boris", 14, Type.FULL)
        )
        addAll(users)
    }
    return users
}


/*
    Получить список пользователей, у которых имеется полный доступ (поле type имеет значение FULL).
 */
fun getUsersWithFullType(users: List<User>): List<User> {
    return users.filter { it.type == Type.FULL }
}