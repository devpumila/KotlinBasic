// '황영덕' 강사님의 '코틀린 프로그래밍 기본 1'의 Final Project 의 TicTacToe 과제 만들기

var x: Int = 0
var y: Int = 0

fun main() {
    var board = Array<CharArray>(3) { CharArray(3) } //3 x 3의 Array 배열 정의
    initBoard(board)  // 배열 초기화

    val names = arrayOf("1P", "2P") //플레이어 이름 정의
    val marks = arrayOf('O', 'X') // 1P는 'O' 2P는 'X'

    play(board, names, marks) //메소드 호출

}

// 플레이 진행하기
fun play(board: Array<CharArray>, names: Array<String>, marks: Array<Char>) {

    var round = 0 //라운드 초기화
    var turn = 0 //턴 초기화

    while (true) {
        println("\n ${turn + 1}번째 턴\n")
        printBoard(board) // 격자 출력
        if (!playerInput(names[turn], board)) //플레이어의 입력정보로 진행
            continue // 만약 false가 반환되면 그대로 다시 while문의 처음으로 돌아가서 실행함
        board[y][x] = marks[turn] //아니라면 반환될 경우 격자에 표기를 함
        round++ // 라운드 횟수 증감

        // 무승부 조건
        // 칸이 9칸 뿐이기 때문에 만약 9칸이 다 채워질동안 승부가 나지 않으면 무승부로 게임을 종료후 메시지 출력
        if (round == 9) {
            println("무승부")
            break
        }

        //승리 조건
        if (isWin(board, x, y)) { //승리조건 판별 메소드에서 true가 반환되어 오면 break로 문 종료
            println("유져 ${names[turn + 1]} Win!")
            break
        }

        // 계속 진행될 경우 turn이 0이면 1p 1이면 2p
        turn = (turn + 1) % 2

    }

}


// 격자판을 공백으로 초기화
fun initBoard(board: Array<CharArray>) {
    for (y in board.indices) {
        for (x in board.indices) {
            board[y][x] = ' ' // 반복문을 통해 보드판에 ' ' 값 대입
        }
    }
}


fun printBoard(board: Array<CharArray>) { //보드 출력을 위한 메소드
    // 가로 줄번호
    print("  ")
    for (x in 0..2) print("$x ")
    println()

    // 세로 줄번호 및 플레이어 표기
    for (y in 0..2) {
        print("$y ")
        for (x in 0..2) {
            print("${board[y][x]}")
            if (x != 2) print("|")
        }
        println()

        // 세로 격자
        if (y != 2) {
            print("  ")
            for (x in 0..2) {
                print("-")
                if (x != 2) print("+")
            }
            println()
        }
    }
}

// 격자 범위에 있는지 검사
val isInRange: (Int, Int) -> Boolean = { x, y -> x in 0..2 && y in 0..2 } // x와 y가 격자 범위에 있는지 Boolean 타입으로 체크


// 격자의 칸이 빈 곳인지 검사하는 메소드
fun isValid(board: Array<CharArray>, x: Int, y: Int): Boolean { // 배열의 x,y값을 받아 조건이 참인지 확인후 boolean타입으로 return한다.
    return isInRange(x, y) && board[y][x] == ' '
}

// 플레이어가 입력하는 부분
fun playerInput(names: String, board: Array<CharArray>): Boolean {
    print("$names 입력(세로,가로): ")
    val input = readLine()!!.split(',') // ' , ' 를 기준으로 나눠서 input배열의 0,1번에 각각 순서대로 저장한다.
    //만약 사용자가 1,2가 아닌 (1,2) 방식으로 입력을 한다면 split을 따로 빼서 input.length 로 길이를 검사하는 방법도 있다.
    y = input[0].toInt()
    x = input[1].toInt()
    if (!isValid(board, x, y)) return false // 격자 빈칸을 체크하는 메소드를 이용하여 빈칸이 있는지 체크하고 빈칸이 아니라면 false를 반환
    return true // 격자 체크후 정상적이라면 true를 반환함
}

// 승리 조건 판별
//이부분 알고리즘은 이해를 아직 못해서 그대로 퍼옴.
fun isWin(board: Array<CharArray>, x: Int, y: Int): Boolean {
    // 가로, 세로, 우하 대각선, 우상 대각선 방향에 대한 x, y 변화량
    val dx = arrayOf(-1, 1, 0, 0, -1, 1, 1, -1)
    val dy = arrayOf(0, 0, -1, 1, -1, 1, -1, 1)

    //arrayOf 함수 참고한거 : https://warmdeveloper.tistory.com/15

    for (d in 0..3) {
        var count = 1 //한개를 놓았으므로 무조건 1부터 카운트가 시작
        for (b in 0..1) {
            val index = 2 * d + b
            var cx = x + dx[index]
            var cy = y + dy[index]
            while (isInRange(cx, cy)) {
                if (board[cy][cx] == board[y][x]) count++
                cx += dx[index]
                cy += dy[index]
            }
        }
        if (count == 3) return true
    }
    return false
}



