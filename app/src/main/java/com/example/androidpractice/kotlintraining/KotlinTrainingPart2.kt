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


/*
    Преобразовать список User в список имен пользователей.
    Получить первый и последний элементы списка и вывести их в лог.
 */
fun printUsersNames(users: List<User>) {
    val names = users.map { it.name }
    names.firstOrNull()?.let { name ->
        println(name)
    }
    names.lastOrNull()?.let { name ->
        println(name)
    }
}


/*
    Создать функцию-расширение класса User, которая проверяет, что юзер старше 18 лет,
    и в случае успеха выводит в лог, а в случае неуспеха возвращает ошибку.
 */
@Throws(Exception::class)
fun User.isAdult() {
    if (age >= 18) {
        println("User is adult")
    } else {
        throw Exception("User is young")
    }
}


/*
    Создать интерфейс AuthCallback с методами authSuccess, authFailed и реализовать анонимный
    объект данного интерфейса. В методах необходимо вывести в лог информацию о статусе авторизации.
 */
interface AuthCallback {

    fun authSuccess()

    fun authFailed()
}

val authLogCallback = object : AuthCallback {
    override fun authSuccess() {
        println("Auth is success")
    }

    override fun authFailed() {
        println("Auth is failed")
    }
}

fun authWithCallback() {
    authLogCallback.authSuccess()
    authLogCallback.authFailed()
}