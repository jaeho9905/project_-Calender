package ts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class Diary_implement extends Log_implement implements Diary_interface {

	int articlesLastIndex;
	Article[] articles;

	Scanner scanner;

	Diary_implement() {
		articles = new Article[100];
		articlesLastIndex = -1;
		members = new Member[100];
		membersLastIndex = -1;
	}

	String getNowDateStr() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat Date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = Date.format(cal.getTime());
		return dateStr;
	}

	Article getArticlesById(int id) {
		for (int i = 0; i <= articlesLastIndex; i++) {
			if (articles[i].id == id) {
				return articles[i];
			}
		}
		return null;
	}

	@Override
	public void doCommandList() {
		if (login_code == true) {
			System.out.println("========  게시물 리스트  ========");

			System.out.println("번호   | 날짜                            | 제목           |내용");

			for (int key : Article.usermap.keySet()) {
				if (user_id.equals(Article.usermap.get(key).user_id)) {
					Article value = Article.usermap.get(key);
					System.out.print(" "+value.id + "       | ");
					System.out.print(value.resDate + "   |   ");
					System.out.print(value.title);
					System.out.println("         |" + value.body);
				}
			}
		} else {
			System.out.println("로그인 상태가 아닙니다");
			doCommandLogin();
		}
	}

	@Override
	public void doCommandAdd() {

		System.out.println("========  게시물 추가  ========");

		Article article = new Article();

		Article lastArticle = null;

		if (articlesLastIndex >= 0) {
			lastArticle = articles[articlesLastIndex];
		}

		int newId;

		if (lastArticle == null) {
			newId = 1;
		} else {
			newId = lastArticle.id + 1;
		}

		article.id = newId;
		article.resDate = getNowDateStr();

		if (login_code == true) {
			System.out.print("제목 : ");
			article.title = scanner.nextLine();
			System.out.println("내용(종료하려면 exit를 입력하세요) : ");
			article.body = "";
			for (;;) {
				article.body1 = scanner.nextLine();
				if (article.body1.equals("exit")) {
					break;
				}
				article.body += article.body1 + "\n        ";
			}

			int articlesNewIndex = articlesLastIndex + 1;
			articles[articlesNewIndex] = article;
			Article.usermap.put(newId, new Article(user_id, newId, article.resDate, article.title, article.body));
			System.out.printf("%d번 글이 생성되었습니다.\n", article.id);

			articlesLastIndex++;
		} else if (login_code == false) {
			System.out.println("글 작성을 위해 로그인이 필요합니다.");
			doCommandLogin();
		}

	}

	@Override
	public void doCommandDelete(int id) {

		System.out.println("======  게시물 삭제 ======");

		for (int key : Article.usermap.keySet()) {
			if (id == Article.usermap.get(key).id && user_id.equals(Article.usermap.get(key).user_id)) {
				Article.usermap.remove(id);

			} else {
				System.out.println("권한이 없거나 입력하신 번호의 게시물이 존재하지않습니다.");
			}
		}

	}

	@Override
	public void doCommandModify(int id) {

		System.out.println("======  게시물 수정 ======");
		Article article = new Article();

		article.resDate = getNowDateStr();
		for (int key : Article.usermap.keySet()) {
			if (id == Article.usermap.get(key).id && user_id.equals(Article.usermap.get(key).user_id)) {
				if (login_code == true) {
					System.out.println("제목 : ");
					article.title = scanner.nextLine();
					System.out.println("내용(종료하려면 exit를 입력하세요) : ");
					article.body = "";
					for (;;) {
						article.body1 = scanner.nextLine();
						if (article.body1.equals("exit")) {
							break;
						}
						article.body += article.body1 + "\n       ";
					}
					Article.usermap.replace(key,
							new Article(user_id, key, article.resDate, article.title, article.body));
				}
			} else {
				System.out.println("권한이 없거나 입력하신 번호의 게시물이 존재하지않습니다.");
			}
		}

	}

	void doSchedule() {
		Diary_implement board = new Diary_implement();
		board.getMonthGalendar();
	}

	public void getMonthGalendar() {

		Calendar cal = Calendar.getInstance(); // 캘린더 객체 생성
		int thisYear = cal.get(Calendar.YEAR); // 현재 년
		int thisMonth = cal.get(Calendar.MONTH) + 1; // 현재 달

		getMonthGalendar(thisYear, thisMonth); // 현재달의 달력을 출력

	}

	@Override
	public void getMonthGalendar(int intYear, int intMmonth) {
		@SuppressWarnings("resource")
		HashMap<String, ArrayList<String>> listMap = new HashMap<String, ArrayList<String>>();
		Calendar cal = Calendar.getInstance();

		int thisYear = cal.get(Calendar.YEAR); // 현재 년
		int thisMonth = cal.get(Calendar.MONTH) + 1; // 현재 달
		int today = cal.get(Calendar.DATE); // 오늘 일자 저장

		boolean booToday = (intYear == thisYear) && (thisMonth == intMmonth);

		cal.set(intYear, intMmonth - 1, 1); // 캘린더객체에 입력받은 년, 달, 그리고 Date을 1로설정

		int sDayNum = cal.get(Calendar.DAY_OF_WEEK); // 1일의 요일 얻어오기
		int endDate = cal.getActualMaximum(Calendar.DATE); // 달의 마지막일 얻기
		int nowYear = cal.get(Calendar.YEAR);
		int nowMonth = cal.get(Calendar.MONTH);

		System.out.println("===================== " + nowYear + "년  " + (nowMonth + 1) + "월 ==================");

		// 요일명 출력
		System.out.println(" 일\t 월\t 화\t 수\t 목\t 금\t 토\t");
		System.out.println("====================================================");

		int intDateNum = 1;

		for (int i = 1; intDateNum <= endDate; i++) {

			if (i < sDayNum)
				System.out.printf("%3s\t", "★"); // i가 요일숫자보다 작으면 공백으로 채우기
			else {
				if (booToday && intDateNum == today)
					System.out.printf("[%2d ]\t", intDateNum); // 오늘 날짜 표시
				else
					System.out.printf("%3d\t", intDateNum); // 일반 출력

				intDateNum++; // 출력할 date 증가.
			}
			if (i % 7 == 0)
				System.out.println();

		} // for
		while (true) {
			Scanner sc = new Scanner(System.in);
			boolean exit = false;
			System.out.println();
			System.out.println("+---------------------------------+");
			System.out.println("| 1. 일정 등록");
			System.out.println("| 2. 일정 검색 및 변경");
			System.out.println("| 3. 종료");
			System.out.println("+---------------------------------+");

			System.out.print("명령 (1.일정 등록  2.일정 검색 및 변경 3.종료 )\n> ");
			char order = sc.next().charAt(0);
			if (login_code == false) {

				switch (order) {

				case '1':

					System.out.print("[일정 등록] 날짜를 입력하세요.(ex.2020-01-01)\n> ");
					String theDate = sc.next();
					System.out.println("날짜 입력완료");
					if (!listMap.containsKey(theDate)) {
						// 기존의 일정이 없다면, 빈 Arraylist를 추가한다.
						ArrayList<String> emptyList = new ArrayList<String>();
						listMap.put(theDate, emptyList);

					}

					System.out.print("[일정 등록] 일정을 입력하세요.\n> ");
					String theList = sc.next();

					ArrayList<String> existList = listMap.get(theDate);

					existList.add(theList);
					listMap.put(theDate, existList);
					System.out.println("일정이 등록되었습니다.");

					break;

				case '2':
					System.out.print("[일정 검색] 날짜를 입력하세요.(ex.2020-01-01)\n> ");
					String findDate = sc.next();

					if (listMap.containsKey(findDate)) {
						// 해당 날짜에 일정이 있다면
						ArrayList<String> schedule = listMap.get(findDate);

						System.out.printf("%d개의 일정이 있습니다.\n", schedule.size());

						for (int i = 0; i < schedule.size(); i++) {
							// 존재하는 일정 전체 출력
							System.out.printf("%d.%s\n", i + 1, schedule.get(i));
						}

						while (true) {
							System.out.print("일정을 변경하시겠습니까?(예:1 / 아니오:2)\n> ");
							String change = sc.next();

							if (change.equals("1")) {
								// 일정 변경한다.
								System.out.println("현재 등록되어 있는 일정입니다.");
								for (int i = 0; i < schedule.size(); i++) {
									// 존재하는 일정 전체 출력
									System.out.printf("%d.%s\n", i + 1, schedule.get(i));
								}
								System.out.print("변경할 일정의 번호를 입력하세요.(숫자만 입력)\n> ");
								int number = sc.nextInt();
								sc.nextLine();

								if (number - 1 >= schedule.size()) {
									// index not exists
									System.out.println("잘 못 입력하셨습니다. 존재하는 일정을 선택하세요.");
									continue;
								} else {
									// index exists
									System.out.print("변경 내용을 입력하세요.\n> ");
									String changeList = sc.next();

									schedule.set(number - 1, changeList);
									
									System.out.println("정상적으로 일정이 변경되었습니다.");

								}
								break;
							}

							else if (change.equals("2")) {
								// 일정 변경하지 않는다.
								break;
							} else {
								// 1이나 2 외에 엉뚱한 값을 입력하면
								System.out.println("잘 못 입력하셨습니다. 다시 선택해주세요.");
							}

						}

					} else {
						// 해당 날짜에 일정이 없다면
						System.out.println("해당 날짜에 일정이 존재하지 않습니다.");
					}
					break;

				case '3':
					System.out.println("스케줄을 종료합니다. 이용해 주셔서 감사합니다.");
//				System.exit(0);
					exit = true;
					break;
				default:
					System.out.println("잘 못 입력하셨습니다. 다시 선택해주세요.");
				}
			}
			if (exit == true)

			{
				break;
			}
		}
	}

}
