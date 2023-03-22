# java-chess

체스 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

----

## 기능 목록

### 체스판
- [x] 초기 체스판을 생성한다.
- [x] 자기 위치로는 움직일 수 없다.
- [x] 경로 내 장애물이 있는 지 검증한다.
- [x] 게임에서 남아있는 말에 대한 점수를 구해야한다.

- [x] King이 잡히는 경우 게임에서 진다. (게임 종료)
- [x] `status` 명령어를 입력하면 각 진영의 점수를 출력하고 어느 진영이 이겼는지 결과를 볼 수 있어야한다. (게임 종료)
- [x] `end` 명령어를 입력하면 프로그램이 종료한다. (프로그램 종료)
- [x] 초기 게임 상태 명령어를 받아서 체스 게임을 새로 시작할지 혹은 기존 게임을 불러올지 정할 수 있다.

### 기물 
- [x] 체스판을 구성하는 요소이다.
- [x] 움직인다.
    - [x] 빈 공간은 움직일 수 없다.
    - [x] pawn은 올바른 위치로 움직일 수 있다.
      - pawn 처음에만 2칸 전진이 가능하다
      - 처음 이후에는 전진만 가능하다
    - [x] rook은 올바른 위치로 움직일 수 있다.
      - 출발 및 도착 위치에 대하여 행의 차이가 0 이거나 열의 차이가 0인 경우에 이동이 가능하다.
    - [x] knight는 올바른 위치로 움직일 수 있다.
      - 출발 및 도착 위치에 대하여 행의 차와 열의 차의 절댓값이 (2,1), (1,2)에 포함되면 이동이 가능하다.
    - [x] bishop는 올바른 위치로 움직일 수 있다.
      - 출발 및 도착 위치에 대하여 행의 차와 열의 차의 절댓값이 같으면 이동이 가능하다.
    - [x] queen은 올바른 위치로 움직일 수 있다.
      - rook과 bishop의 움지이는 조건이 만족하면 이동이 가능하다.
    - [x] king은 올바른 위치로 움직일 수 있다.
      - 출발 및 도착 위치에 대하여 행의 차와 열의 차의 절댓값이 (0,1), (1,0), (1,1)에 포함되면 이동이 가능하다.

### 위치 
- [x] 유효한 위치인지 검증한다.

### 경로
- [x] 출발 위치와 도착 위치 사이의 경로를 반환해준다

### 입력
- [x] 게임 명령어를 입력 받는다(start, end, move)

### 출력
- [x] 게임 시작 문구를 출력한다.
- [x] 체스판 현황을 출력한다.

### DB
- DB 계획도
  - Room (room_id, board_id, lowerTeam, upperTeam) --> 추가 미션 예정
  - board (board_id, position(String), piece(String), isLowerTeamTurn)
  

### DB 테이블 DDL (추가 미션 전)
Board 
```sql
CREATE TABLE `chess`.`board` (
  `board_id` INT NOT NULL,
  `position` VARCHAR(300) NOT NULL,
  `piece` VARCHAR(300) NOT NULL,
  `isLowerTeamTurn` TINYINT NOT NULL,
  PRIMARY KEY (`board_id`));
```



