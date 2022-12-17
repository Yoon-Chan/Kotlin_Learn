package chap07.schap02

import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import java.io.FileWriter


fun main() {
    //c1()
    //c2()
    c3()
}

fun c1(){
    /*
    *   파일과 I/O 스트림

    *
    *   다음 함수를 사용하면 스트림의 전체 콘텐츠를 읽어올 수 있다.
    *   fun InputStream.readBytes() : ByteArray
    *   fun Reader.readText() : String
    *   fun Reader.readLines() : Line<String>

    *   BufferedReader 클래스에 있는 readLine() 메서드는 스트림에서 한 줄을 가져오지만,
    *   위에 적힌 readText(), readLines()는 스트림 끝까지 콘텐츠를 읽어서 전체를 한 문자열이나 각 줄을 나타내는 문자열의 리스트로 반환한다.
    *
    * */

    FileWriter("data.txt").use { it.write("One\nTwo\nThree") }

    //One
    FileReader("data.txt").buffered().use { println(it.readLine()) }

    //One Two Three
    FileReader("data.txt").use { println(it.readText().replace("\n", " ")) }

    //[One, Two, Three]
    println(FileReader("data.txt").readLines())

    FileInputStream("data.txt").buffered().use {
        var sum = 0
        for(byte in it) sum += byte
    }

    FileReader("data.txt").buffered().use {
        for(line in it.lineSequence()) println(line)
    }

    //forEachLine()과 useLines() 함수는 줄 단위 이터레이션을 허용한다. 이 두 함수는 스트림을 자동으로 닫기 때문에
    // 스트림을 닫는 일을 신경 쓰지 않아도 된다.

    //One, Two, Three
    FileReader("data.txt").useLines { println(it.joinToString()) }

    // One/Two/Three
    FileReader("data.txt").forEachLine { print("$it/") }

}

fun c2(){
    /*
    *   스트림 생성
    *
    *   bufferedReaders()/ bufferedWriter() 확장 함수를 사용하면 지정한 FIle 객체에 대해
    *   BufferedReader/ BufferedWriter 인스턴스를 만들 수 있다.
    * */

    val file = File("data.txt")

    file.bufferedWriter().use { it.write("Hello!") }
    file.bufferedReader().use { println(it.readLine()) }    // Hello!

    /*
    *   위와 비슷한 함수로 reader()/ writer() 확장 함수도 있다. 이들은 각각 버퍼가 없는
    *   FileReader/ FileWriter 객체를 만든다.
    * */
}

fun c3(){
    /*
    *  URL 유틸리티
    *
    *   코틀린 표준 라이브러리는 URL 객체의 주소로부터 네트워크 연결을 통해 데이터를 읽어오는 몇 가지 도우미 함수를 제공한다.
    *   fun URL.readText(charset : Charset = Charsets.UTF_8) : String
    *   readText 함수는 URL 인스턴스에 해당하는 입력 스트림 콘텐츠를 전부 읽어온다.
    *
    *   fun URL.readBytes() : ByteArray
    *   입력 이진 스트림의 콘텐츠를 바이트 배열로 읽어온다.
    *
    *   두 함수 모두 전체 스트림 콘텐츠를 읽어오는 작업이 완료될 때까지 스레드를 블럭시키므로, 큰 파일을 다운로드할 때는 이 두 함수를 사용하면 안된다.
    *
    * */

    /*
    *   파일 콘텐츠 접근하기
    *   I/O 스트림을 쓰지 않고도 파일 콘텐츠를 읽을 수 있는 특별한 함수를 제공한다.
    *   이런 함수는 전체 파일을 읽고 쓰거나, 데이터를 기존 파일 뒤에 추가하거나, 한 줄씩 파일을 처리해야 할 때 유용하다.
    *
    *   readText() : 파일 콘텐츠 전부를 한 문자열로 읽어온다.
    *   readLines() : 파일 콘텐츠 전부를 줄 구분 문자를 사용해 줄 단위로 나눠 읽어서 문자열의 리스트를 반환한다.
    *   writeText() 파일 콘텐츠를 주어진 문자열로 설정한다. 필요하면 파일을 덮어 쓴다.
    *   appendText() 주어진 문자열을 파일의 콘텐츠 뒤에 추가한다.
    * */

    val file = File("data.txt")

    file.writeText("One")
    println(file.readText())    //One

    file.appendText("\nTwo")
    println(file.readLines())   //[One, Two]

    file.writeText("Three")
    println(file.readLines())   //[Three]

    //forEachLine() 함수를 사용하면 파일 전체를 읽지 않고 텍스트 콘텐츠를 한 줄씩 처리할 수 있다.
    file.writeText("One\nTwo\nThree")
    file.forEachLine { print("/$it") }  // /One/Two/Three

    //useLines() 함수는 주어진 란다에 줄의 시퀀스를 전달해준다. 이렇게 받은 시퀀스를 사용해 어떤 결과를 계산하고, 이 결과는 다시 useLine()의 결과로 반환된다.

    println(file.useLines { lines -> lines.count() {it.length > 3} })   //1

}