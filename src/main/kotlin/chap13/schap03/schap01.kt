package chap13.schap03

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.random.Random

suspend fun main() {
    //채널 예제
/*
    ex1() 실행 결과

    Sending : 1
    Receiving : 1
    Sending : 4
    Receiving : 4
    Sending : 9
    Receiving : 9
    Sending : 16
    Sending : 25
    Receiving : 16
    Receiving : 25
    */
    //ex1()


/*
    Client #1 : deposit 50
    Deposited 50
    Withdrawn 100
    Client #2: withdraw 100 (OK)
    Client #2: balance is 50
    Client #1: balance is 50
    Client #0: withdraw 1000 (DENIED)
    Client #0: balance is 50
    */

    runBlocking {
        val manager = accountManager(100)
        withContext(Dispatchers.Default) {
            launch {
                manager.deposit("Client #1", 50)
                manager.printBalance("Client #1")
            }

            launch {
                manager.tryWithdraw("Client #2", 100)
                manager.printBalance("Client #2")
            }
        }

        manager.tryWithdraw("Client #0", 1000)
        manager.printBalance("Client #0")
        manager.close()

    }
}


fun c1(){
    /*
    *
    *   동시성 통신
    *
    *   스레드 안전성을 유지하면서 여러 동시성 작업 사이에 효율적으로 데이터를 공유할 수 있게 해주는 코루틴 라이브러리
    *   고급 기능을 이야기 한다.
    *
    * */

    /*
    *   채널
    *
    *   임의의 데이터 스트림을 코루틴 사이에 공유할 수 있는 편리한 방법이다.
    *   Channel 인터페이스가 제공하는 채널에 대한 기본 연산은 데이터를 보내는 send() 메서드와
    *   데이터를 받는 receive() 메서드이다.
    *
    *   제네릭 Channel() 함수를 사용해 채널을 만들 수 있다. 이 함수는 채널의 용량을 지정하는 정숫값을 받는다.
    *   채널 기본 구현은 크기가 정해진 내부 버퍼를 사용한다.
    *   버퍼가 꽉 차면 최소 하나 이상의 채널 원소가 상대방에 의해 수신될 때까지 send() 호출이 일시 중단 된다.
    *   버퍼가 비어있으면 누군가 최소 하나 이상의 원소를 채널로 송신할 때까지 receive() 호출이 일시 중단된다.
    *
    *   Channel() 함수는 채널의 동작을 바꿀 수 있는 여러 특별한 값을 받을 수 있다. 이러한 값은
    *   Channel 인터페이스의 동반 객체에 상수로 정의돼 있다.
    *       - Channel.UNLIMITED (= Int.MAX_VALUE)
    *       - Channel.RENDEZVOUS (= 0)
    *       - Channel.CONFLATED( = -1)
    * */
}

fun c2(){
    /*
    *   생산자
    * 
    *   produce() 라는 코루틴 빌더가 있다. 이 빌더는 채널과 비슷한 send() 메서드를 제공하는 ProducerScope 영역을 도입해준다.
    * */
    
    /*
    *   티커
    *   코루틴 라이브러리에는 티커(ticker)라고 하는 특별한 랑데부 채널이 있다.
    *   이 채널은 Unit 값을 계속 발생시키되 한 원소와 다음 워소의 발생 시점이 주어진 지연 시간만큼 떨어져 있는 스트림을 만든다.
    *   이 채널을 만들려면 ticker() 함수를 사용해야 한다.
    *   이 함수를 호출할 때 다음을 지정할 수 있다.
    * 
    *       - delayMillis : 티커 원소의 발생 시간 간격을 밀리초 단위로 지정한다.
    *       - initialDelayMillis : 티커 생성 시점과 원소가  최초로 발생하는 시점 사이의 시간 간격이다.
    *       - context : 티커를 실행할 코루틴 문맥이다.(디폴트는 빈 문맥이다.)
    *       - mode : 티커의 행동을 결정하는 TickerMode 이넘이다.
    *           - TickerMode.FIXED_PERIOD : 생성되는 원소 사이의 시간 간격을 지정된 지연시간에 최대한 맞추기 위해 실제 지연 시간을 조정한다.
    *           - TickerMode.FIXED_DELAY : 실제 흘러간 시간과 관계없이 delayMillis로 지정한 지연 시간만큼 시간을 지연시킨 후 다음 원소를 송신한다.
    * */
    
    /*
    *   액터
    *   가변 상태를 스레드 안전하게 공유하는 방법을 구현하는 일반적인 방법으로 액터(actor) 모델이 있다.
    *   액터는 내부 상태와 다른 액터에게 메시지를 보내서 동시성 통신을 진행할 수 있는 수단을 제공하는 객체이다.
    *   actor() 빌더는 결과를 생성해내는 것이 목적이 아닌 잡을 시작한다는 점에서 lauch()와 비슷하지만 CoroutineExceptionHandler에
    *   의존하는 launch()와 같은 예외 처리 정책을 따른다.
    *
    *
    * 
    * */
    
}

suspend fun ex1(){
    runBlocking {
        val streamSize = 5
        val channel = Channel<Int>(3)   //채널 용량 3

        launch {
            for(n in 1..streamSize){

                delay(Random.nextLong(100))
                val square = n*n
                println("Sending : $square")
                channel.send(square)
            }
        }

        launch {
            for(i in 1..streamSize){
                delay(Random.nextLong(100))
                val n = channel.receive()
                println("Receiving : $n")
            }
        }
    }
}

//코루틴이 종료되면 produce() 빌더가 채널을 자동으로 닫아준다.
//produce() 안에서 예외가 발생하면 예외를 저장했다가 해당 채널에 대해 receive()를 가장 처음 호출한 코루틴 쪽에 예외가 다시 던져진다.
suspend fun ex2(){
    runBlocking { 
        val channel = produce { 
            for(n in 1..5){
                val square = n*n
                println("Sending : $square")
                send(square)
            }
        }
        
        launch { 
            //명시적인 이터레이션을 사용하지 않고 consumeEach() 함수를 통해 모든 채널 콘텐츠를 얻어서 사용할 수 있다.
            channel.consumeEach { println("Receiving : $it") }
        }
    }
    
}

sealed class AccountMessage

class GetBalance(
    val amount : CompletableDeferred<Long>
) : AccountMessage()

class Deposit(val amount : Long) : AccountMessage()

class Withdraw(
    val amount : Long,
    val isPermitted : CompletableDeferred<Boolean>
) : AccountMessage()

fun CoroutineScope.accountManager(
    initialBalance: Long
) = actor<AccountMessage> {
    var balance = initialBalance

    for(message in channel){
        when(message) {
            is GetBalance -> message.amount.complete(balance)

            is Deposit ->{
                balance += message.amount
                println("Deposited ${message.amount}")
            }

            is Withdraw -> {
                val canWithdraw = balance >= message.amount
                if(canWithdraw) {
                    balance -= message.amount
                    println("Withdrawn ${message.amount}")
                }

                // CompletableDeferred 에서 complete() 메서드를 사용하는 부분이다.
                // 애터 클라이언트에게 요청 결과를 돌려줄 때는 이 방법을 사용한다.
                message.isPermitted.complete(canWithdraw)
            }
        }
    }
}

private suspend fun SendChannel<AccountMessage>.deposit(
    name: String,
    amount : Long
){
    send(Deposit(amount))
    println("$name : deposit $amount")
}

private suspend fun SendChannel<AccountMessage>.tryWithdraw(
    name: String,
    amount : Long
){
    val status = CompletableDeferred<Boolean>().let {
        send(Withdraw(amount, it))
        if(it.await()) "OK" else "DENIED"
    }
    println("$name: withdraw $amount ($status)")
}

private suspend fun SendChannel<AccountMessage>.printBalance(
    name: String
){
    val balance = CompletableDeferred<Long>().let {
        send(GetBalance(it))
        it.await()
    }
    println("$name: balance is $balance")
}
