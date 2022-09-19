package com.wafflestudio.seminar.test

/**
 * TODO
 *   3번을 코틀린으로 다시 한번 풀어봐요.
 *   객체를 통한 구조화를 시도해보면 좋아요 :)
 */

class Database(private val data: MutableList<String>) {
    private var pointer: Int = 0
    private val recentDeleted = mutableListOf<Pair<Int, String>>()
    
    fun moveUp(by: Int) {
        if (pointer - by < 0) {
            throw Exception("Error 100")
        }
        pointer -= by
    }
    
    fun moveDown(by: Int) {
        if (pointer + by >= data.count()) {
            throw Exception("Error 100")
        }
        pointer += by
    }
    
    fun delete() {
        val removed = data.removeAt(pointer)
        recentDeleted.add(Pair(pointer, removed))
        if (pointer == data.count()) {
            pointer = data.count() - 1
        }
    }
    
    fun restore() {
        if (recentDeleted.isEmpty()) {
            throw Exception("Error 200")
        }
        val popped = recentDeleted.removeLast()
        data.add(popped.first, popped.second)
        if (popped.first <= pointer) {
            pointer += 1
        }
    }
    
    fun list() {
        data.forEach { student -> println(student) }
    }
}
class Program(private val database: Database) {
    
    fun parse(command: String) {
        if (command == "restore") {
            database.restore()
        } else if (command == "delete") {
            database.delete()
        } else if (command == "list") {
            database.list()
        } else if (command.startsWith("move")) {
            val direction = command.split(" ")[1]
            val by = command.split(" ").last().toInt()
            if (direction == "-u") {
                database.moveUp(by)
            } else {
                database.moveDown(by)
            }
        }
        
    }
    
}

fun main() {
    val studentList = readLine()!!
        .removeSurrounding("[", "]")
        .replace("\"", "")
        .split(",")
        .toMutableList()
    
    val db = Database(studentList)
    val program = Program(db)
    
    while (true) {
        val command = readLine() ?: break
        if (command == "q") {
            break
        }
        try {
        program.parse(command)
        } catch (e: Exception) {
            println(e.message)
        }
    }
    
}