import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainMenu extends Application {
    int inputType; // For checking user input (1 = Login & 2 = Register)
    int queueMode; // For checking collection simulator type (1 = FIFO & 2 = PriorityQueue)
    int userType = 1; // For checking user type (1 = Donor & 2 = NGO)
    int userIndex = 1; // For determining user index (if index is in json file or not)

    // JSON files needed
    File ngoFile = new File("ngo.json");
    File donorFile = new File("donor.json");
    File distributedFile = new File("distributed.json");

    // edit
    Queue<String> queue = new LinkedList<String>();
    PriorityQueue<NGO> priorityQueue = new PriorityQueue<NGO>(Collections.reverseOrder());

    Scene MainMenu, UserType, Login, Register, UserMenu, AidMenu, tableMenu, allAidsTableMenu, CollectAllAidsType, CollectAidsType, CollectAidsTableMenu;

    // Find screen size to set scene size
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    double width = screenSize.getWidth()*0.4;
    double height = screenSize.getHeight()*0.6;
    double buttonSize = width/4.8;   

    @Override
    public void start(Stage Main) {

        //-------------------- MENUS --------------------//
        // MAIN MENU
        // Elements
        Label mmLabel = new Label("Hello! Welcome to the System.");
        Button mmLogin = new Button("Login");
        Button mmRegister = new Button("Register");
        Button mmAllAids = new Button("Show All Aids");
        Button mmCollectAids = new Button("Collect Aids");
        Button mmQuit = new Button("Quit");
        VBox MainMenuBox = new VBox(20);
        // Formatting
        mmLogin.setPrefWidth(buttonSize);
        mmRegister.setPrefWidth(buttonSize);
        mmAllAids.setPrefWidth(buttonSize);
        mmCollectAids.setPrefWidth(buttonSize);
        mmQuit.setPrefWidth(buttonSize);
        MainMenuBox.setAlignment(Pos.CENTER);
        MainMenuBox.getChildren().addAll(mmLabel, mmRegister, mmLogin, mmAllAids, mmCollectAids, mmQuit);
        // Scene
        MainMenu = new Scene(MainMenuBox, width, height);


        // USER TYPE MENU (Donor or NGO)
        // Elements
        Label utmLabel = new Label("Please choose user type.");
        Button utmDonor = new Button("Donor");
        Button utmNGO = new Button("NGO");
        VBox UserTypeBox = new VBox(20);
        // Formatting
        utmDonor.setPrefWidth(buttonSize);
        utmNGO.setPrefWidth(buttonSize);
        UserTypeBox.setAlignment(Pos.CENTER);
        UserTypeBox.getChildren().addAll(utmLabel, utmDonor, utmNGO);
        // Scene
        UserType = new Scene(UserTypeBox, width, height);

        // LOGIN MENU
        // Elements
        GridPane LoginPane = new GridPane();        
        LoginPane.add(new Label("Username "), 0, 0);
        TextField lmUserFld = new TextField();
        LoginPane.add(lmUserFld, 1, 0);
        LoginPane.add(new Label("Password "), 0, 2);
        PasswordField lmPassFld = new PasswordField();
        LoginPane.add(lmPassFld, 1, 2);
        Label lmStatus = new Label("");
        LoginPane.add(lmStatus, 1, 4);
        HBox LoginBox = new HBox(20);
        Button lmBtn = new Button("Login");
        Button lmBackBtn = new Button("Back");
        LoginBox.getChildren().addAll(lmBtn, lmBackBtn);
        LoginPane.add(LoginBox, 1, 3);
        // Formatting
        LoginPane.setAlignment(Pos.CENTER);
        LoginPane.setHgap(3);
        LoginPane.setVgap(3);
        // Scene
        Login = new Scene(LoginPane, width, height);

        // REGISTER MENUS
        // Elements
        GridPane RegisterPane = new GridPane();
        RegisterPane.add(new Label("Username "), 0, 0);
        TextField rmUserFld = new TextField();
        RegisterPane.add(rmUserFld, 1, 0);
        RegisterPane.add(new Label("Password "), 0, 2);
        PasswordField rmPassFld = new PasswordField();
        RegisterPane.add(rmPassFld, 1, 2);
        Label rmExtraLbl = new Label();
        RegisterPane.add(rmExtraLbl, 0, 3);
        TextField rmExtraFld = new TextField();
        RegisterPane.add(rmExtraFld, 1, 3);
        HBox RegisterBox = new HBox(20);
        Button rmBtn = new Button("Register");
        Button rmBackBtn = new Button("Back");
        RegisterBox.getChildren().addAll(rmBtn, rmBackBtn);
        RegisterPane.add(RegisterBox, 1, 4);
        Label rmStatus = new Label();
        RegisterPane.add(rmStatus, 1, 5);
        // Formatting
        RegisterPane.setAlignment(Pos.CENTER);
        RegisterPane.setHgap(3);
        RegisterPane.setVgap(3);
        // Scene
        Register = new Scene(RegisterPane, width, height);

        // USER MENU
        //Elements
        Label umLabel = new Label();
        Button umAction = new Button();
        Button umView = new Button();
        Button umLogout = new Button("Logout");
        VBox UserMenuBox = new VBox(20);
        UserMenuBox.getChildren().addAll(umLabel, umAction, umView, umLogout);
        // Formatting
        umAction.setPrefWidth(buttonSize);
        umView.setPrefWidth(buttonSize);
        umLogout.setPrefWidth(buttonSize);
        UserMenuBox.setAlignment(Pos.CENTER);
        // Scene
        UserMenu = new Scene(UserMenuBox, width, height);

        // AID MENU (Donate or Request Aid)
        // Elements
        GridPane AidPane = new GridPane();
        Label amTypeLbl = new Label();
        AidPane.add(amTypeLbl, 0, 0);
        TextField amTypeFld = new TextField();
        AidPane.add(amTypeFld, 1, 0);
        AidPane.add(new Label("Quantity of the Aid"), 0, 2);
        TextField amAmountFld = new TextField();
        AidPane.add(amAmountFld, 1, 2);
        HBox AidMenuBox = new HBox(20);
        Button amAddBtn = new Button("Add");
        Button amBackBtn = new Button("Back");
        AidMenuBox.getChildren().addAll(amAddBtn, amBackBtn);
        AidPane.add(AidMenuBox, 1, 3);
        Label amStatus = new Label();
        AidPane.add(amStatus, 1, 4);
        // Formatting
        AidPane.setAlignment(Pos.CENTER);
        AidPane.setHgap(3);
        AidPane.setVgap(3);
        // Scene
        AidMenu = new Scene(AidPane, width, height);

        // TABLE MENU (Show Donor or NGO Aids)
        // Elements    
        TableView<Aid> TableMenuView = new TableView<>();
        TableColumn<Aid, String> tmName = new TableColumn<>("Aid Name");
        tmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Aid, Integer> tmQty = new TableColumn<>("Aid Quantity");
        tmQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Aid, String> tmDonor = new TableColumn<>("Aid Donor");
        tmDonor.setCellValueFactory(new PropertyValueFactory<>("donor"));
        TableColumn<Aid, String> tmNGO = new TableColumn<>("NGO");
        tmNGO.setCellValueFactory(new PropertyValueFactory<>("ngo"));
        TableColumn<Aid, String> tmStatus = new TableColumn<>("Status");
        tmStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        TableMenuView.getColumns().addAll(Arrays.asList(tmName, tmQty, tmDonor, tmNGO, tmStatus));
        Button tbBackBtn = new Button("Back");	
        VBox TableMenuBox = new VBox(TableMenuView, tbBackBtn);
        // Formatting
        tbBackBtn.setPrefWidth(buttonSize);
        tmName.prefWidthProperty().bind(TableMenuView.widthProperty().multiply(0.25));
        tmQty.prefWidthProperty().bind(TableMenuView.widthProperty().multiply(0.2));
        tmDonor.prefWidthProperty().bind(TableMenuView.widthProperty().multiply(0.2));
        tmNGO.prefWidthProperty().bind(TableMenuView.widthProperty().multiply(0.2));
        tmStatus.prefWidthProperty().bind(TableMenuView.widthProperty().multiply(0.15));
        TableMenuBox.setPadding(new Insets(10));
        TableMenuBox.setSpacing(5);
        TableMenuBox.setAlignment(Pos.CENTER);
        // Scene
        tableMenu = new Scene(TableMenuBox, width, height);

        // ALL AIDS MENU (Show All Aids)
        // Elements
        TableView<Distribution> AllAidsMenuView = new TableView<>();
        TableColumn<Distribution, String> aamDonor = new TableColumn<>("Donor");
        aamDonor.setCellValueFactory(new PropertyValueFactory<>("Donor"));
        TableColumn<Distribution, String> aamPhone = new TableColumn<>("Phone");
        aamPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        TableColumn<Distribution, String> aamAid = new TableColumn<>("Aid");
        aamAid.setCellValueFactory(new PropertyValueFactory<>("Aid"));
        TableColumn<Distribution, String> aamQty = new TableColumn<>("Quantity");
        aamQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        TableColumn<Distribution, String> aamNGO = new TableColumn<>("NGO");
        aamNGO.setCellValueFactory(new PropertyValueFactory<>("ngo"));
        TableColumn<Distribution, String> aamManpower = new TableColumn<>("Manpower");
        aamManpower.setCellValueFactory(new PropertyValueFactory<>("Manpower"));
        TableColumn<Distribution, String> aamStatus = new TableColumn<>("Status");
        aamStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));       
        AllAidsMenuView.getColumns().addAll(Arrays.asList(aamDonor, aamPhone, aamAid, aamQty, aamNGO, aamManpower, aamStatus));
        Button aamBackBtn = new Button("Back");        
        VBox AllAidsBox = new VBox(AllAidsMenuView, aamBackBtn);
        // Formatting
        aamBackBtn.setPrefWidth(buttonSize);
        aamDonor.prefWidthProperty().bind(AllAidsMenuView.widthProperty().multiply(0.125));
        aamPhone.prefWidthProperty().bind(AllAidsMenuView.widthProperty().multiply(0.15));
        aamAid.prefWidthProperty().bind(AllAidsMenuView.widthProperty().multiply(0.2));
        aamQty.prefWidthProperty().bind(AllAidsMenuView.widthProperty().multiply(0.1));
        aamNGO.prefWidthProperty().bind(AllAidsMenuView.widthProperty().multiply(0.125));
        aamManpower.prefWidthProperty().bind(AllAidsMenuView.widthProperty().multiply(0.15));
        aamStatus.prefWidthProperty().bind(AllAidsMenuView.widthProperty().multiply(0.15));
        AllAidsBox.setPadding(new Insets(10));
        AllAidsBox.setSpacing(5);
        AllAidsBox.setAlignment(Pos.CENTER);
        // Scene
        allAidsTableMenu = new Scene(AllAidsBox, width, height);

        // COLLECT AIDS TYPE MENU (FIFO or PriorityQueue)
        // Elements
        Label catmLabel = new Label("Please choose queue mode.");
        Button catmFIFO = new Button("FIFO Queue");
        Button catmPriority = new Button("Priority Queue");
        VBox CollectAidsTypeBox = new VBox(20);
        // Formatting
        catmFIFO.setPrefWidth(buttonSize);
        catmPriority.setPrefWidth(buttonSize);
        CollectAidsTypeBox.setAlignment(Pos.CENTER);
        CollectAidsTypeBox.getChildren().addAll(catmLabel, catmFIFO, catmPriority);
        // Scene
        CollectAidsType = new Scene(CollectAidsTypeBox, width, height);

        // COLLECT AIDS MENU (Aid Collection Simulator)
        
        // Elements
        TableView<Distribution> CollectAidsMenuView = new TableView<>();
        TableColumn<Distribution, String> camDonor = new TableColumn<>("Donor");
        camDonor.setCellValueFactory(new PropertyValueFactory<>("Donor"));
        TableColumn<Distribution, String> camPhone = new TableColumn<>("Phone");
        camPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        TableColumn<Distribution, String> camAid = new TableColumn<>("Aid");
        camAid.setCellValueFactory(new PropertyValueFactory<>("Aid"));
        TableColumn<Distribution, String> camQty = new TableColumn<>("Quantity");
        camQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        TableColumn<Distribution, String> camNGO = new TableColumn<>("NGO");
        camNGO.setCellValueFactory(new PropertyValueFactory<>("ngo"));
        TableColumn<Distribution, String> camManpower = new TableColumn<>("Manpower");
        camManpower.setCellValueFactory(new PropertyValueFactory<>("Manpower"));
        TableColumn<Distribution, String> camStatus = new TableColumn<>("Status");
        camStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        CollectAidsMenuView.getColumns().addAll(Arrays.asList(camDonor, camPhone, camAid, camQty, camNGO, camManpower, camStatus));
        GridPane CollectAidsPane1 = new GridPane();
        Label camQueue = new Label("Queue");
        CollectAidsPane1.add(camQueue, 0, 0);
        @SuppressWarnings("rawtypes")
        ListView camQueueListView = new ListView();
        CollectAidsPane1.add(camQueueListView, 1, 0);
        GridPane CollectAidsPane2 = new GridPane();
        CollectAidsPane2.add(new Label("NGO "), 0, 0);
        TextField camNGOFld = new TextField();
        CollectAidsPane2.add(camNGOFld, 1, 0);
        Label camCollectStatus = new Label("");
        Button camEnqueueBtn = new Button("Enqueue an NGO");    
        Button camDequeueBtn = new Button("Dequeue an NGO"); 
        Button camBackBtn = new Button("Back"); 
        VBox CollectAidsBox = new VBox(CollectAidsMenuView, CollectAidsPane1, CollectAidsPane2, camCollectStatus, camEnqueueBtn, camDequeueBtn, camBackBtn);
        // Formatting
        camDonor.prefWidthProperty().bind(CollectAidsMenuView.widthProperty().multiply(0.125));
        camPhone.prefWidthProperty().bind(CollectAidsMenuView.widthProperty().multiply(0.15));
        camAid.prefWidthProperty().bind(CollectAidsMenuView.widthProperty().multiply(0.2));
        camQty.prefWidthProperty().bind(CollectAidsMenuView.widthProperty().multiply(0.1));
        camNGO.prefWidthProperty().bind(CollectAidsMenuView.widthProperty().multiply(0.125));
        camManpower.prefWidthProperty().bind(CollectAidsMenuView.widthProperty().multiply(0.15));
        camStatus.prefWidthProperty().bind(CollectAidsMenuView.widthProperty().multiply(0.15));
        camEnqueueBtn.setPrefWidth(buttonSize);
        camDequeueBtn.setPrefWidth(buttonSize);
        camBackBtn.setPrefWidth(buttonSize);
        camQueueListView.setPrefHeight(height/3.3);
        camQueueListView.setPrefWidth(width*0.89);
        camQueueListView.setOrientation(Orientation.HORIZONTAL);
        CollectAidsBox.setPadding(new Insets(10));
        CollectAidsBox.setSpacing(5);
        CollectAidsBox.setAlignment(Pos.CENTER);
        CollectAidsPane1.setHgap(5);
        CollectAidsPane2.setAlignment(Pos.CENTER);
        CollectAidsPane2.setHgap(5);     
        // Scene
        CollectAidsTableMenu = new Scene(CollectAidsBox, width, height);
        

        //-------------------- BUTTON FUNCTIONS --------------------//    
        // MAIN MENU BUTTONS
        mmLogin.setOnAction(e -> {
            inputType = 1;
            Main.setTitle("User Type Menu");
            Main.setScene(UserType);
        });
        mmRegister.setOnAction(e -> {
            inputType = 2;
            Main.setTitle("User Type Menu");
            Main.setScene(UserType);
        });
        mmAllAids.setOnAction(e -> {
            populateTable(AllAidsMenuView);
            Main.setTitle("All Aids Table");
            Main.setScene(allAidsTableMenu);
        });
        mmCollectAids.setOnAction(e -> {
            Main.setTitle("Queue Mode Menu");
            Main.setScene(CollectAidsType);
            populateTable(CollectAidsMenuView);
        });
        mmQuit.setOnAction(e -> Main.close());

        // USER TYPE MENU BUTTONS
        utmDonor.setOnAction(e -> { // go to donor login or register
            userType = 1;
            if (inputType == 1) {
                Main.setTitle("Login Menu");
                Main.setScene(Login);
            }
            else if (inputType == 2) {
                Main.setTitle("Register Menu");
                rmExtraLbl.setText("Phone Number ");
                Main.setScene(Register);
            }

        });
        utmNGO.setOnAction(e -> { // go to ngo login or register
            userType = 2;
            if (inputType == 1) {
                Main.setTitle("Login Menu");
                Main.setScene(Login);
            }
            else if (inputType == 2) {
                Main.setTitle("Register Menu");
                rmExtraLbl.setText("Manpower ");
                Main.setScene(Register);
            }
            
        });

        // LOGIN MENU BUTTONS        
        lmBtn.setOnAction(e -> {
            String user = String.valueOf(lmUserFld.getText());
            String password = String.valueOf(lmPassFld.getText());
      
            AccountManagement manageAccount = new AccountManagement();
      
            int loginSuccess;
            if (userType == 1)
                loginSuccess = manageAccount.checkUserIndex(donorFile, false, user, password, true);
            else
                loginSuccess = manageAccount.checkUserIndex(ngoFile, true, user, password, true);
            
            if (loginSuccess != -1) {
                if (userType == 1) {
                    umLabel.setText("Donor Menu");
                    umAction.setText("Donate Aid");
                    umView.setText("View Aids Donated");
                    Main.setTitle("Donor Menu");
                    Main.setScene(UserMenu);
                }
                else {
                    umLabel.setText("NGO Menu");
                    umAction.setText("Request Aid");
                    umView.setText("View Aids Requested");
                    Main.setTitle("NGO Menu");
                    Main.setScene(UserMenu);
                }
                userIndex = loginSuccess;
            } else
                lmStatus.setText("Login Failed.");
        });
        lmBackBtn.setOnAction(e -> {
            inputType = 0;
            userType = 0;
            lmUserFld.setText("");
            lmPassFld.setText("");
            lmStatus.setText("");
            Main.setTitle("Main Menu");
            Main.setScene(MainMenu);
        });

        // REGISTER MENU BUTTONS
        rmBtn.setOnAction(e -> {
            String user = String.valueOf(rmUserFld.getText());
            String password = String.valueOf(rmPassFld.getText());
            String extra = String.valueOf(rmExtraFld.getText());

            AccountManagement manageAccount = new AccountManagement();

            int registerSuccess;
            try {
                if (user == "" || password == "" || extra == "")
                    throw new IllegalArgumentException();

                if (userType == 1)
                    registerSuccess = manageAccount.register(false, donorFile, user, password, extra);
                else
                    registerSuccess = manageAccount.register(true, ngoFile, user, password, extra);

                if (registerSuccess != -1) {
                    userIndex = registerSuccess;
                    rmStatus.setText(String.valueOf("Register Success."));
                    rmUserFld.setText("");
                    rmPassFld.setText("");
                    rmExtraFld.setText("");
                } else
                    rmStatus.setText("Register Failed. User Already Exists.");
                
            } catch (NumberFormatException ex) {
                rmStatus.setText("Manpower must be a number.");
            }
             catch (IllegalArgumentException ex) {
                rmStatus.setText("Fields cannot be empty.");
            }
            
        });
        rmBackBtn.setOnAction(e -> {
            inputType = 0;
            userType = 0;
            rmUserFld.setText("");
            rmPassFld.setText("");
            rmExtraFld.setText("");
            rmStatus.setText("");
            Main.setTitle("Main Menu");
            Main.setScene(MainMenu);
        });

        // USER MENU BUTTONS
        umAction.setOnAction(e -> {
            if (userType == 1) {
                Main.setTitle("Donate Aid Menu");
                amTypeLbl.setText("Aid to be Donated");
            }
            else {
                Main.setTitle("Request Aid Menu");
                amTypeLbl.setText("Aid Needed");
            }
            Main.setScene(AidMenu);
        });
        umView.setOnAction(e -> {
            
            UserInterface userInterface = new UserInterface(donorFile,ngoFile,distributedFile);
            if (userType == 1) {
                ArrayList<Aid> aidList = userInterface.listAids(false, userIndex);
                for (int i = 0; i < aidList.size(); i++)
                    TableMenuView.getItems().add(aidList.get(i));
                Main.setTitle("Donated Aids Table");
            }
            else {
                ArrayList<Aid> aidList = userInterface.listAids(true, userIndex);
                for (int i = 0; i < aidList.size(); i++)
                    TableMenuView.getItems().add(aidList.get(i));
                Main.setTitle("Requested Aids Table");                     	           
            }
            Main.setScene(tableMenu);            
        });
        umLogout.setOnAction(e -> {
            inputType = 0;
            userType = 0;
            rmUserFld.setText("");
            rmPassFld.setText("");
            rmExtraFld.setText("");
            rmStatus.setText("");
            lmUserFld.setText("");
            lmPassFld.setText("");
            lmStatus.setText("");
            Main.setTitle("Main Menu");
            Main.setScene(MainMenu);
        });

        // AID MENU BUTTONS
        amAddBtn.setOnAction(e -> {
            UserInterface userInterface = new UserInterface(donorFile,ngoFile,distributedFile);
            String aidType = String.valueOf(amTypeFld.getText()).toLowerCase();
            String aidAmountString = String.valueOf(amAmountFld.getText());

            try {
                if (aidType == "" || aidAmountString == "")
                    throw new IllegalArgumentException();

                int aidAmount = Integer.parseInt(aidAmountString);
                
                if (userType == 1) {
                    userInterface.donateAid(false, userIndex, aidType, aidAmount);
                }
                else if (userType == 2) {
                    userInterface.requestAid(true, userIndex, aidType, aidAmount);
                }
                amStatus.setText("Success!");
                amTypeFld.setText("");
                amAmountFld.setText("");
            } catch (NumberFormatException ex) {
                amStatus.setText("Aid Quantity must be a number.");
            }
            catch (IllegalArgumentException ex) {
                amStatus.setText("Fields cannot be empty.");
            }      
            catch (Exception ex) {
                amStatus.setText("Unsuccessful.");
            }
            
        });
        amBackBtn.setOnAction(e -> {
            amTypeFld.setText("");
            amAmountFld.setText("");
            amStatus.setText("");
            Main.setScene(UserMenu);
        });

        // TABLE MENU BUTTONS
        tbBackBtn.setOnAction(e -> {
            TableMenuView.getItems().clear();
            Main.setScene(UserMenu);
        });


        // ALL AIDS MENU BUTTONS
        aamBackBtn.setOnAction(e -> {
            AllAidsMenuView.getItems().clear();
            Main.setScene(MainMenu);
        });

        // COLLECT AIDS TYPE MENU BUTTONS
        catmFIFO.setOnAction(e -> { // go to fifo queue collection simulator
            queueMode = 1;
            Main.setTitle("FIFO Queue Simulator Menu");
            Main.setScene(CollectAidsTableMenu);
        });
        catmPriority.setOnAction(e -> { // go to priority queue collection simulator
            queueMode = 2;
            Main.setTitle("Priority Queue Simulator Menu");
            Main.setScene(CollectAidsTableMenu);
        });

        // COLLECT AIDS MENU BUTTONS
        camEnqueueBtn.setOnAction(e -> {
            camCollectStatus.setText("");
            DistributionCollector collector = new DistributionCollector(distributedFile, ngoFile);
            String name = String.valueOf(camNGOFld.getText());
            try {
                if (queueMode == 1) {
                    collector.EnqueueFIFOQueue(queue, name);
                    populateList(queue, camQueueListView);
                } else if (queueMode == 2) {
                    collector.EnqueuePriorityQueue(priorityQueue, name);
                    Queue<NGO> sortedQueue = collector.getSortedPriorityQueue(priorityQueue);
                    populateList(sortedQueue, camQueueListView);
                }
                camCollectStatus.setText("Successfully Enqueued");
            } catch (InputMismatchException ex) {
                camCollectStatus.setText(ex.getMessage());
            }
            camNGOFld.setText("");
        });
        camDequeueBtn.setOnAction(e -> {
            camCollectStatus.setText("");
            DistributionCollector collector = new DistributionCollector(distributedFile, ngoFile);
            try {
                if (queueMode == 1) {
                    collector.DequeueCollectorQueue(queue);
                    populateList(queue, camQueueListView);
                } else if (queueMode == 2) {
                    collector.DequeueCollectorQueue(priorityQueue);
                    Queue<NGO> sortedQueue = collector.getSortedPriorityQueue(priorityQueue);
                    populateList(sortedQueue, camQueueListView);
                }
                camCollectStatus.setText("Successfully Dequeued");
            } catch (Exception ex) {
                camCollectStatus.setText("Queue is empty.");
            }
            CollectAidsMenuView.getItems().clear();
            populateTable(CollectAidsMenuView);
        });
        camBackBtn.setOnAction(e -> {
            camCollectStatus.setText("");
            camNGOFld.setText("");
            CollectAidsMenuView.getItems().clear();
            camQueueListView.getItems().clear();
            if (queueMode == 1) {
                queue.clear();
            } else if (queueMode == 2) {
                priorityQueue.clear();
            }
            queueMode = 0;
            Main.setScene(MainMenu);
        });


        // Set MainMenu as Main Scene
        Main.setTitle("Main Menu");
        Main.setScene(MainMenu);
        Main.show();
    }

        private void populateTable(TableView<Distribution> table) { // function to populate table with all values in donor.json, ngo,json, and distribution.json
        DistributionView distributionView = new DistributionView(donorFile, ngoFile, distributedFile);
        ArrayList<Distribution> allAids = distributionView.viewAllAids();
        for (int i = 0; i < allAids.size(); i++)
            table.getItems().add(allAids.get(i));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private <E> void populateList(Queue<E> queue, ListView list) { // function to populate list with values in the queue or priorityQueue
        ObservableList<E> toOL = FXCollections.observableArrayList(queue);
        list.setItems( toOL);
    }

    

    public static void main(String[] args) {
        launch(args);
    }

}
