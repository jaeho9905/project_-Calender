package ts;

import java.util.Scanner;

public class Sys_implement extends Diary_implement implements Sys_interface {

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	@Override
	public void mainMenu() {

		System.out.println("======== 메뉴 ========\n");
		System.out.println("회원가입: signup\n스케줄: schedule\n종료: exit");
		while (true) {
			System.out.print("메뉴를 입력해주세요: ");
			String menu = scanner.nextLine();
			if (menu.equals("signup")) {
				doCommandSignup();
				break;
			} else if (menu.equals("schedule")) {
				doSchedule();
				break;
			} else if (menu.equals("exit")) {
				doCommandExit();
				break;
			} else {
				System.out.println("다시 입력해주세요");
				continue;
			}
		}
	}

	@Override
	public void start() {
		mainMenu();
		while (true) {

			System.out.println("명령어를 보시려면 'help'를 입력해주세요.");
			String command = scanner.next();

			if (command.equals("help")) {
				scanner.nextLine();
				doCommandHelp();
			} else if (command.equals("list")) {
				scanner.nextLine();
				doCommandList();
			} else if (command.equals("add")) {
				scanner.nextLine();
				doCommandAdd();
			} else if (command.equals("delete")) {
				scanner.nextLine();
				if (login_code == true) {
					System.out.printf("삭제 할 게시물 번호 입력 : ");
					int idToDelete = scanner.nextInt();
					doCommandDelete(idToDelete);
				}else {
					System.out.println("로그인 상태가 아닙니다.");
				}
			} else if (command.equals("modify")) {
				scanner.nextLine();
				if (login_code == true) {
				System.out.printf("수정 할 게시물 번호 입력 : ");
				int idToModify = scanner.nextInt();
				scanner.nextLine();
				doCommandModify(idToModify);
				}else {
					System.out.println("로그인 상태가 아닙니다.");
				}
			} else if (command.equals("schedule")) {
				scanner.nextLine();
				doSchedule();
			} else if (command.equals("signup")) {
				scanner.nextLine();
				doCommandSignup();
			} else if (command.equals("login")) {
				scanner.nextLine();
				doCommandLogin();

			} else if (command.equals("logout")) {
				scanner.nextLine();
				doCommandLogout();
			} else if (command.equals("exit")) {
				scanner.nextLine();
				doCommandExit();

			} else {
				scanner.nextLine();
				System.out.println("잘못된 명령어 입니다.");
			}
		}
	}

	@Override
	public void showHelp() {
		System.out.println("======  메인 ======");
		System.out.println("help : 명령어 리스트");
		System.out.println("add : 일기 추가");
		System.out.println("modify : 일기 수정");
		System.out.println("delete : 일기 삭제");
		System.out.println("list : 일기 리스트 보기");
		System.out.println("schedule : 스케줄 확인하기");
		System.out.println("exit 일기장 종료");
		System.out.println("signup : 회원 가입");
		System.out.println("login : 로그인");
		System.out.println("logout : 로그아웃");
		System.out.println();
	}

	@Override
	public void doCommandHelp() {
		showHelp();
	}

	@Override
	public void doCommandExit() {
		System.out.println("======  프로그램 종료 ======");
		System.exit(0);
	}

}
