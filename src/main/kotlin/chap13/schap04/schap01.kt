package chap13.schap04

import kotlin.concurrent.thread
import kotlin.concurrent.timer

suspend fun main() {
/*
    ex1() 실행 결과

    Starting a thread...
    Worker : 1
    Worker : 2
    Worker : 3
    Worker : 4
    Shutting down...
    */
    //ex1()


/*
    Starting a thread...
    Worker: 1
    Worker: 2
    Worker: 3
    Worker: 4
    Shutting down...
            */
    ex2()
}

fun c1(){
    /*
    *   자바 동시성 사용하기
    *
    *   스레드 시작하기
    *    - 범용 스레드를 시작하려면, 스레드에서 실행하려는 실행가능(Runnable) 객체에 대응하는 람다와
    *      스레드 프로퍼티들을 지정해서 thread() 함수를 사용하면 된다.
    *
    *   start : 스레드를 생성하자마자 시작할지 여부(디폴트 true)
    *   isDaemon : 스레드를 데몬 모드로 시작할지 여부(디폴트는 false). 데몬 스레드는 JVM의 종료를 방해하지
    *              않고 메인 스레드가 종료될 때 자동으로 함께 종료된다.
    *   contextClassLoader : 스레드 코드가 클래스와 자원을 적재할 때 사용할 클래스 로더(디폴트는 null)
    *   name : 커스텀 스레드 이름, 디폴트는 널인데, 이는 JVM이 이름을 자동으로 지정한다는 뜻이다.
    *   priority : Thread.MIN_PRIORITY(=1)부터 Thread.MAX_PRIORITY(=10) 사이의 값으로 정해지는 우선순위로
    *              어떤 스레드가 다른 스레드에 비해 얼마나 많은 CPU 시간을 배정받는지 결정한다.
    *              디폴트 값은 -1이며, 이 값은 자동으로 우선순위를 정하라는 뜻이다.
    *   block : ()-> Unit 타입의 함숫값으로 새 스레드가 생성되면 실행할 코드이다.
    *
    *
    *   다른 함수로는 어떤 지정한 시간 간격으로 동작을 수행하는 자바 타이머 관련 함수가 있다.
    *   timer() 함수는 어떤 작업을 이전 작업이 끝난 시점을 기준으로 고정된 시간 간격으로 실행하는 타이머를 설정한다.
    *   그 결과, 어떤 작업이 시간이 오래 걸리면 이후의 모든 실행이 연기된다.
    *   timer() 호출로 설정할 때 다음 옵션을 지정할 수 있다.
    *       - name : 타이머 스레드의 이름(디폴트는 널)
    *       - daemon : 타이머 스레드를 데몬 스레드로 할지 여부(디폴트는 false)
    *       - startAt : 최초로 타이머 이벤트가 발생하는 시간을 나타내는 Date객체
    *       - period : 연속된 타이머 이벤트 사이의 시간 간격(밀리초 단위)
    *       - action : 타이머 이벤트가 발생할 때마다 실행될 TimeTask.() -> Unit 타입의 람다
    *
    *   또 다른 방법으로는 inialDelay 파라미터가 있는(이 값의 디폴트는 0) 오버로딩된 다른 timer()를 사용할 수 있다.
    *
    *   두 타이머 이벤트 사이의 시간 간격을 최대한 일정하게 맞춰주는 fixedRateTimer() 함수도 있다.
    * */


    /*
    *   동기화와 락
    *
    *   일반적으로 synchronized() 함수는 람다의 반환값을 반환한다.
    *   코틀린에서는 @Synchronized 애너테이션을 통해 같은 목적을 달성할 수 있다.
    *   표준 라이브러리에는 동기화 블록과 비슷한 어떤 Lock 객체를 사용해 주어진 람다를 실행하게 해주는 withLock() 함수도 있다.
    *   withLock() 함수를 사용하면 함수가 알아서 락을 풀어주므로, 예외가 발생할 때 락을 푸는 것을 신경 쓰지 않아도 된다.
    *
    * */
}

//150밀리초마다 메시지를 출력하는 스레드를 시작하는 예제
suspend fun ex1(){
    println("Starting a thread...")

    thread(name = "Worker", isDaemon = true){
        for(i in 1..5){
            println("${Thread.currentThread().name} : $i")
            Thread.sleep(150)
        }
    }

    Thread.sleep(500)
    println("Shutting down...")
}

suspend fun ex2(){
    println("Starting a thread...")
    var count = 0

    timer(period = 150, name = "Worker", daemon = true){
        println("${Thread.currentThread().name}: ${++count}")
    }

    Thread.sleep(500)
    println("Shutting down...")

}

//동기화와 락 예제
suspend fun ex3(){
    var counter = 0
    val lock = Any()

    for(i in 1..5){
        thread(isDaemon = false) {
            synchronized(lock){
                counter += i
                println(counter)
            }
        }
    }
}