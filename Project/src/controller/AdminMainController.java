package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminMainController implements Initializable {
	@FXML
	private Label lbcurrenttime;
	@FXML
	private Label lbcurrentadminname;
	@FXML
	private Label lbseatno1;
	@FXML
	private Label lbseatno2;
	@FXML
	private Label lbseatno3;
	@FXML
	private Label lbseatno4;
	@FXML
	private Label lbseatno5;
	@FXML
	private Label lbseatno6;
	@FXML
	private Label lbseatno7;
	@FXML
	private Label lbseatno8;
	@FXML
	private Label lbseatinfo1;
	@FXML
	private Label lbseatinfo2;
	@FXML
	private Label lbseatinfo3;
	@FXML
	private Label lbseatinfo4;
	@FXML
	private Label lbseatinfo5;
	@FXML
	private Label lbseatinfo6;
	@FXML
	private Label lbseatinfo7;
	@FXML
	private Label lbseatinfo8;
	@FXML
	private Label lbremaintime1;
	@FXML
	private Label lbremaintime2;
	@FXML
	private Label lbremaintime3;
	@FXML
	private Label lbremaintime4;
	@FXML
	private Label lbremaintime5;
	@FXML
	private Label lbremaintime6;
	@FXML
	private Label lbremaintime7;
	@FXML
	private Label lbremaintime8;
	@FXML
	private Button btlogout;
	@FXML
	private Button btordercheck;
	@FXML
	private Button btmemberinfo;
	@FXML
	private Button btstock;
	@FXML
	private Button btsales;
	
	final DateFormat format = DateFormat.getInstance();
	
	private LocalTime t;
	
	private int i1;
	private int i2;
	private int i3;
	private int i4;
	private int i5;
	private int i6;
	private int i7;
	private int i8;
	String remaintime;
	private ExecutorService executorService; //쓰레드풀을 생성하여 병렬처리를 할 수 있음
	private int countdownseconds;
	
	//로그인감지
	private ServerSocket serverSocket;
	private Socket socket;
	//Vector는 자동 동기화를 보장하므로 멀티스레드환경에서 안정적으로 사용가능
	private List<User> list = new Vector<User>();
	private boolean logout = true;
	ClientLoginController clc = new ClientLoginController();
	//private int no[] = new int[2];
	private int index = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbcurrentadminname.setText(AdminLoginController.currentadminid);
		//로그인이 되면 서버소켓이 켜지고 대기상태
		serverSocket();
		//---------------------------------------현재시간------------------------------------------------

		Timeline timeline = new Timeline(
				new KeyFrame(
						Duration.seconds(1), new EventHandler() {
			@Override
			public void handle(Event event) {
				final Calendar cal = Calendar.getInstance();
				lbcurrenttime.setText(format.format(cal.getTime()));
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
/*
		Thread clock = new Thread(new Runnable() {
			@Override
			public void run() {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				while(true) {
					String strDate = sdf.format(new Date());
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		clock.start();
*/
	
	}

	
	//서버소켓 켜지고 대기상태
	public void serverSocket() {
		executorService = Executors.newFixedThreadPool(13);
		try {
			serverSocket = new ServerSocket(5000);
		}catch(IOException e) {
			e.printStackTrace();
		}
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						System.out.println("접속대기중");
						Socket socket = serverSocket.accept();//대기상태
						User user = new User(socket);
						list.add(user);
						try {
							Thread.sleep(100);
						}catch(InterruptedException e) {
							e.printStackTrace();
						}
					}catch(IOException e) {
						if(serverSocket.isClosed()) {
							break;
						}
						e.printStackTrace();
					}
				}//while end
			}//run() end
		};//Runnable end
		executorService.submit(runnable);
		//executorService.shutdown();
	}

	//접속한 사용자 내장클래스 - 지역클래스
	public class User{
		private Socket socket = null;
		private String id = null;
		private BufferedReader in;
		private int seatno;
		private boolean starttime = false;
		
		//생성자
		public User(Socket socket) {
			this.socket = socket;
			receive();
		}
		
		void receive() {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}catch(IOException e) {
				System.out.println("IOException");
			}
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						logout = true;
						String str;
						while(logout) {
							str = in.readLine();
							if(str.startsWith("ID")) { //문자열이 ID로 시작하면 true
								System.out.println("손님이 로그인 했습니다");
								System.out.println("str = " + str); //str = "ID kjh"
								String command = str.substring(3); //command = "kjh"
								id = command; //id = "kjh"
								System.out.println(id);
								
								ChargeDAO cdo = new ChargeDAO();
								seatno = Integer.parseInt(cdo.getLoginUserSeatNo(id));
								System.out.println(id + "의 charge테이블 좌석번호 변경" + seatno);
								int prepaidmoney = cdo.selectNameTime(id);
								int countdownseconds = cdo.selectTime(id);
								
								starttime = true;
								/*
								if(prepaidmoney == 1000) { //1000원 1시간
									countdownseconds = 3600; 
								}else if(prepaidmoney == 2000) { //2000원 2시간
									countdownseconds = 7200;
								}else if(prepaidmoney == 3000) { //3000원 3시간
									countdownseconds = 10800;
								}else if(prepaidmoney == 5000) { //5000원 6시간
									countdownseconds = 21600;
								}else if(prepaidmoney == 10000) { //10000원 12시간
									countdownseconds = 43200;
								}
								*/
								
								Runnable runn = new Runnable() {
									@Override
									public void run() {
										if(id ==null) {
											CommonDialog.alertDisplay(1, "오류", "유저이름이 없음", "오류");
										}else {
											if(seatno == 1) {
												while(starttime) {
													for(i1=countdownseconds; i1>=0; i1--) {
													//for(i1 = availabletime1; i1>=0; i1--) {
														try {
															Thread.sleep(1000);
															System.out.println("1번자리 쓰레드");
														}catch(InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbseatinfo1.setText(id);
															lbremaintime1.setText(String.valueOf(i1/3600 + ":" + i1%3600/60 + ":" + i1%3600%60));	
														});
														if(starttime == false) {
															break;
														}
													}// for문 end
												}//while문 end
											}else if(seatno == 2) {
												while(starttime) {
													for(i2=countdownseconds; i2>=0; i2--) {
														try {
															Thread.sleep(1000);
															System.out.println("2번자리 쓰레드");
														}catch(InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbseatinfo2.setText(id);
															lbremaintime2.setText(String.valueOf(i2/3600 + ":" + i2%3600/60 + ":" + i2%3600%60));	
														});
														if(starttime == false) {
															break;
														}
													}// for문 end
												}//while문 end
											}else if(seatno == 3) {
												while(starttime) {
													for(i3=countdownseconds; i3>=0; i3--) {
														try {
															Thread.sleep(1000);
															System.out.println("3번자리 쓰레드");
														}catch(InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbseatinfo3.setText(id);
															lbremaintime3.setText(String.valueOf(i3/3600 + ":" + i3%3600/60 + ":" + i3%3600%60));	
														});
														if(starttime == false) {
															break;
														}
													}// for문 end
												}//while문 end
											}else if(seatno == 4) {
												while(starttime) {
													for(i4=countdownseconds; i4>=0; i4--) {
														try {
															Thread.sleep(1000);
															System.out.println("4번자리 쓰레드");
														}catch(InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbseatinfo4.setText(id);
															lbremaintime4.setText(String.valueOf(i4/3600 + ":" + i4%3600/60 + ":" + i4%3600%60));	
														});
														if(starttime == false) {
															break;
														}
													}// for문 end
												}//while문 end
											}else if(seatno == 5) {
												while(starttime) {
													for(i5=countdownseconds; i5>=0; i5--) {
														try {
															Thread.sleep(1000);
															System.out.println("5번자리 쓰레드");
														}catch(InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbseatinfo5.setText(id);
															lbremaintime5.setText(String.valueOf(i5/3600 + ":" + i5%3600/60 + ":" + i5%3600%60));	
														});
														if(starttime == false) {
															break;
														}
													}// for문 end
												}//while문 end
											}else if(seatno == 6) {
												while(starttime) {
													for(i6=countdownseconds; i6>=0; i6--) {
														try {
															Thread.sleep(1000);
															System.out.println("6번자리 쓰레드");
														}catch(InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbseatinfo6.setText(id);
															lbremaintime6.setText(String.valueOf(i6/3600 + ":" + i6%3600/60 + ":" + i6%3600%60));	
														});
														if(starttime == false) {
															break;
														}
													}// for문 end
												}//while문 end
											}else if(seatno == 7) {
												while(starttime) {
													for(i7=countdownseconds; i7>=0; i7--) {
														try {
															Thread.sleep(1000);
															System.out.println("7번자리 쓰레드");
														}catch(InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbseatinfo7.setText(id);
															lbremaintime7.setText(String.valueOf(i7/3600 + ":" + i7%3600/60 + ":" + i7%3600%60));	
														});
														if(starttime == false) {
															break;
														}
													}// for문 end
												}//while문 end
											}else if(seatno == 8) {
												while(starttime) {
													for(i8=countdownseconds; i8>=0; i8--) {
														try {
															Thread.sleep(1000);
															System.out.println("8번자리 쓰레드");
														}catch(InterruptedException e) {
															e.printStackTrace();
														}
														Platform.runLater(() -> {
															lbseatinfo8.setText(id);
															lbremaintime8.setText(String.valueOf(i8/3600 + ":" + i8%3600/60 + ":" + i8%3600%60));	
														});
														if(starttime == false) {
															break;
														}
													}// for문 end
												}//while문 end
											}
										}
									}//run() end
								};//runn Runnalbe() end
								executorService.submit(runn);
								
							}// str.startsWith("ID") if문 end
							
							else if(str.startsWith("EXIT")) {
								logout = false;
								starttime = false;
								System.out.println(str);
								try {
									Thread.sleep(1000);
								}catch(InterruptedException e) {
									e.printStackTrace();
								}
								if(seatno == 1) {
									Platform.runLater(() -> {
										lbseatinfo1.setText("빈좌석");
										lbremaintime1.setText("00:00:00");
									});
								}else if(seatno == 2) {
									Platform.runLater(() -> {
										lbseatinfo2.setText("빈좌석");
										lbremaintime2.setText("00:00:00");
									});
								}else if(seatno == 3) {
									Platform.runLater(() -> {
										lbseatinfo3.setText("빈좌석");
										lbremaintime3.setText("00:00:00");
									});
								}else if(seatno == 4) {
									Platform.runLater(() -> {
										lbseatinfo4.setText("빈좌석");
										lbremaintime4.setText("00:00:00");
									});
								}else if(seatno == 5) {
									Platform.runLater(() -> {
										lbseatinfo5.setText("빈좌석");
										lbremaintime5.setText("00:00:00");
									});
								}else if(seatno == 6) {
									Platform.runLater(() -> {
										lbseatinfo6.setText("빈좌석");
										lbremaintime6.setText("00:00:00");
									});
								}else if(seatno == 7) {
									Platform.runLater(() -> {
										lbseatinfo7.setText("빈좌석");
										lbremaintime7.setText("00:00:00");
									});
								}else if(seatno == 8) {
									Platform.runLater(() -> {
										lbseatinfo8.setText("빈좌석");
										lbremaintime8.setText("00:00:00");
									});
								}
								
								//소켓 닫음
								socket.close();
								
								
								for(User user : list) {
									if(user.id.equals(id)) {
										list.remove(user);
									}
								}
								break;
							}
						}//while문 end
					}catch(IOException e) {
						e.printStackTrace();
					}
				}//run() end
			};//Runnable() end
			executorService.submit(runnable);
		}//receive end
	}//User Class end

	//로그아웃
	public void LogoutAction(ActionEvent event) throws Exception{
		Stage stage = (Stage) btlogout.getScene().getWindow();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/AdminLoginLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		stage.close();
	}
	
	//회원정보 버튼
	public void MemberInfoAction(ActionEvent event) throws Exception{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/javafx/MemberListLayout.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("회원정보");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
}
