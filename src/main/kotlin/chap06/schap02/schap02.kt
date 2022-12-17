package chap06.schap02

data class Person(val firstName : String,
    val familyName : String,
    val age : Int)

//데이터 클래스에서 함수를 생성할 수 있다.
fun Person.show() = println("$firstName $familyName : $age")


data class Person2(val firstName : String,
                  val familyName : String){
    var age = 0
}

data class Mailbox(val address : String, val person : Person)

fun main() {
    c2()
}


fun c2(){
    /*
    *   데이터 클래스
    *
    *   데이터를 저장하기 위한 목적으로 주로 쓰이는 클래스를 선언하는 유용한 기능을 제공한다.
    *
    * */

    val person1 = Person("John", "Doe", 25)
    val person2 = Person("John", "Doe", 25)
    val person3 = person1

    // 만약 data class가 아닌 일반 class라면 첫 번째 println은 false가 된다.
    println(person1 == person2) //true
    println(person1 == person3) // true, 같은 정체성

    val box1 = Mailbox("Unknown", Person("John", "Doe", 25))
    val box2 = Mailbox("Unknown", Person("John", "Doe", 25))

    //만약 Person이 일반 class인 경우 false가 나온다.
    println(box1 == box2)   //true

    val p1 = Person2("John", "Doe").apply { age = 25}
    val p2 = Person2("John", "Doe").apply { age = 0 }

    println(p1 == p2) //true

    person1.show()          // John Doe : 25
    person1.copy().show()   // John Doe : 25
    person1.copy(familyName = "Smith").show() // John Smith : 25
    person1.copy(age = 30, firstName = "Jane").show()   // Jane Doe : 30
}

