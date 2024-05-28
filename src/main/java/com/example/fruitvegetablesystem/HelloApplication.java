package com.example.fruitvegetablesystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloApplication extends Application {
    private Button salesButton;
    private File usersFile = new File("users.ser");
    private List<User> users = new ArrayList<>();
    private Scene loginScene;
    private Scene homeScene;
    private Scene inventoryScene;
    private Scene rateListScene;
    private Scene salesScene;
    private Scene stockListScene;
    private Scene signUpScene;
    private TextField signUpUsernameField;
    private TextField signUpEmailField;
    private ChoiceBox<String> signUpGenderChoice;
    private PasswordField signUpPasswordField;
    private TextField userNameField;
    private PasswordField passwordField;
    private ObservableList<OrderItem> orderData = FXCollections.observableArrayList();
    private Label totalLabel = new Label("Total: Rs. 0.0");
    private TableView<Product> tableView;
    private TextField productIdField;
    private TextField productNameField;
    private TextField stockField;
    private ComboBox<String> typeComboBox;
    private TextField priceField;
    private ComboBox<String> statusComboBox;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private ObservableList<Product> productList;
    private double totalIncome = 0.0;
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Login Interface");
        // Load users from file
        loadUsers();
        // Login scene
        GridPane loginGrid = createLoginGrid(primaryStage);
        loginGrid.setStyle("-fx-background-color: darkgreen;");

        Text welcomeText = new Text("Login to the Fruit & Vegetable Shop");
        welcomeText.setFont(Font.font("Time new Roman", FontWeight.BOLD, 23));
        welcomeText.setFill(javafx.scene.paint.Color.WHITE);
        loginGrid.add(welcomeText, 0, 0, 2, 1); // Spanning 2 columns

        // Load the background image
        FileInputStream name = new FileInputStream("/vegetable-and-fruit-fresh-logo-vector.jpg");
        Image image = new Image(name);
        ImageView imageview = new ImageView(image);
        imageview.setFitWidth(100);  // Set the width of the icon
        imageview.setFitHeight(100); // Preserve aspect ratio
        GridPane.setHalignment(imageview, HPos.CENTER);
        GridPane.setValignment(imageview, VPos.CENTER);
        loginGrid.add(imageview, 0, 1, 2, 1);

        VBox signUpVBox = createSignUpVBox(primaryStage);
        Text signupText = new Text("Signup to the shop");
        signupText.setFont(Font.font("Algerian", FontWeight.BOLD, 20));
        signupText.setFill(javafx.scene.paint.Color.WHITE);
        signUpVBox.getChildren().add(0, signupText);

        // Button actions
        // Login button
        Button loginButton = (Button) loginGrid.lookup("#loginButton");
        loginButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        loginButton.setOnAction(e -> handleLogin(primaryStage));

        // Sign up button
        Button signUpButton = (Button) loginGrid.lookup("#signUpButton");
        signUpButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        signUpButton.setOnAction(e -> primaryStage.setScene(getSignUpScene(signUpVBox)));
        // Initial scene
        loginScene = new Scene(loginGrid, 1500, 1000); // Adjusted size to fit image
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Create home scene
        homeScene = createHomeScene(primaryStage);

        // Create sign up scene
        signUpScene = getSignUpScene(signUpVBox);

    }
    /////////////////////////START OF LOGIN FUNCTIONALITY/////////////////////////////////////
    private GridPane createLoginGrid(Stage primaryStage) throws FileNotFoundException {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        FileInputStream name = new FileInputStream("/username.jpg");
        Image image = new Image(name);
        ImageView imageview = new ImageView(image);
        imageview.setFitWidth(20);  // Set the width of the icon
        imageview.setFitHeight(20);

        // Create the labels and text fields
        Label userNameLabel = new Label("Username:", imageview);
        userNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        userNameLabel.setStyle("-fx-text-fill: white;");
        grid.add(userNameLabel, 0, 2);

        userNameField = new TextField();
        userNameField.setMaxWidth(170);
        grid.add(userNameField, 1, 2);

        FileInputStream pass = new FileInputStream("/password.jpg");
        Image passw = new Image(pass);
        ImageView password = new ImageView(passw);
        password.setFitWidth(20);  // Set the width of the icon
        password.setFitHeight(20);
        Label passwordLabel = new Label("Password:", password);
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        passwordLabel.setStyle("-fx-text-fill: white;");
        grid.add(passwordLabel, 0, 3);

        passwordField = new PasswordField();
        passwordField.setMaxWidth(170);
        grid.add(passwordField, 1, 3);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");
        grid.add(loginButton, 1, 4);

        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        forgotPasswordLink.setOnAction(e -> primaryStage.setScene(getForgotPasswordScene(primaryStage)));
        forgotPasswordLink.setStyle("-fx-text-fill: lightblue;");

        // HBox to group login button and hyperlink
        HBox hBox = new HBox(15); // spacing of 15 units between button and hyperlink
        hBox.setAlignment(Pos.CENTER_LEFT); // align items to the left
        hBox.getChildren().addAll(loginButton, forgotPasswordLink);
        grid.add(hBox, 1, 4);

        // Sign Up button
        Button signUpButton = new Button("Sign Up");
        signUpButton.setId("signUpButton");
        grid.add(signUpButton, 1, 5);

        return grid;
    }

    private Scene getForgotPasswordScene(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: green;");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: white;");
        grid.add(usernameLabel, 0, 0);

        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 0);

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-text-fill: white;");
        grid.add(emailLabel, 0, 1);

        TextField emailField = new TextField();
        grid.add(emailField, 1, 1);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        submitButton.setOnAction(e -> handleForgotPasswordSubmit(usernameField.getText(), emailField.getText(), primaryStage));
        grid.add(submitButton, 1, 2);

        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> primaryStage.setScene(loginScene));
        grid.add(backButton, 1, 3);

        return new Scene(grid, 1500, 1000);
    }

    private void handleForgotPasswordSubmit(String username, String email, Stage primaryStage) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getEmail().equals(email)) {
                primaryStage.setScene(getResetPasswordScene(user, primaryStage));
                return;
            }
        }
        showAlert(Alert.AlertType.ERROR, "Error", "Username and Email do not match.");
    }

    private Scene getResetPasswordScene(User user, Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: darkgreen;");

        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.setStyle("-fx-text-fill: white;");
        grid.add(newPasswordLabel, 0, 0);

        PasswordField newPasswordField = new PasswordField();
        grid.add(newPasswordField, 1, 0);

        Button resetButton = new Button("Reset Password");
        resetButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        resetButton.setOnAction(e -> handlePasswordReset(user, newPasswordField.getText(), primaryStage));
        grid.add(resetButton, 1, 1);

        return new Scene(grid, 1500, 1000);
    }
    private void handlePasswordReset(User user, String newPassword, Stage primaryStage) {
        if (newPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "New password cannot be empty.");
            return;
        }

        user.setPassword(newPassword);
        saveUsers();
        showAlert(Alert.AlertType.INFORMATION, "INFORMATION", "Password reset successfully.");
        primaryStage.setScene(loginScene);
    }
    /////////////////////SAVE USER INFO////////////////////////////////////
    private void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////READ USER INFO///////////////////////////////////////////////
    private void loadUsers() {
        if (!usersFile.exists()) {
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(usersFile))) {
            users = (List<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//////////////////////////END OF LOGIN FUNCTIONALITY//////////////////////////////////////////

    ////////////////////////START OF SIGN UP FUNCTIONALITY/////////////////////////////////////////
    private VBox createSignUpVBox(Stage primaryStage) throws FileNotFoundException {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(25, 25, 25, 25));
        vBox.setStyle("-fx-background-color: green;");
        // Username
        FileInputStream name= new FileInputStream("/username.jpg");
        Image image = new Image(name);
        ImageView imageview=new ImageView(image);
        imageview.setFitWidth(20);  // Set the width of the icon
        imageview.setFitHeight(20);
        Label usernameLabel = new Label("Username:",imageview);
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        usernameLabel.setStyle("-fx-text-fill: white;");
        vBox.getChildren().add(usernameLabel);

        signUpUsernameField = new TextField();
        signUpUsernameField.setMaxWidth(150);
        vBox.getChildren().add(signUpUsernameField);

        // Email
        FileInputStream mail= new FileInputStream("/email.jpg");
        Image email = new Image(mail);
        ImageView icon=new ImageView(email);
        icon.setFitWidth(20);  // Set the width of the icon
        icon.setFitHeight(20);
        Label emailLabel = new Label("Email:",icon);
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        emailLabel.setStyle("-fx-text-fill: white;");
        vBox.getChildren().add(emailLabel);

        signUpEmailField = new TextField();
        signUpEmailField.setMaxWidth(150);
        vBox.getChildren().add(signUpEmailField);

        // Gender
        FileInputStream gend= new FileInputStream("/gender.jpg");
        Image gender = new Image(gend);
        ImageView gen=new ImageView(gender);
        gen.setFitWidth(20);  // Set the width of the icon
        gen.setFitHeight(20);
        Label genderLabel = new Label("Gender:",gen);
        genderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        genderLabel.setStyle("-fx-text-fill: white;");
        vBox.getChildren().add(genderLabel);

        signUpGenderChoice = new ChoiceBox<>(FXCollections.observableArrayList("Male", "Female", "Other"));
        vBox.getChildren().add(signUpGenderChoice);

        // Password
        FileInputStream pass= new FileInputStream("/password.jpg");
        Image passw = new Image(pass);
        ImageView password=new ImageView(passw);
        password.setFitWidth(20);  // Set the width of the icon
        password.setFitHeight(20);
        Label passwordLabel = new Label("Password:",password);
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        passwordLabel.setStyle("-fx-text-fill: white;");
        vBox.getChildren().add(passwordLabel);

        signUpPasswordField = new PasswordField();
        signUpPasswordField.setMaxWidth(150);
        vBox.getChildren().add(signUpPasswordField);

        Button createButton = new Button("Create Account");
        createButton. setStyle("-fx-background-color: black; -fx-text-fill: white;");
        createButton.setOnAction(e -> handleSignUp(primaryStage));
        vBox.getChildren().add(createButton);

        Button backButton = new Button("Back to Login");
        backButton. setStyle("-fx-background-color: black; -fx-text-fill: white;");
        backButton.setOnAction(e -> primaryStage.setScene(loginScene));
        vBox.getChildren().add(backButton);

        return vBox;
    }
    private Scene getSignUpScene(VBox signUpVBox) {
        GridPane signUpGrid = new GridPane();
        signUpGrid.setAlignment(Pos.CENTER);
        signUpGrid.setPadding(new Insets(25, 25, 25, 25));
        signUpGrid.setStyle("-fx-background-color: green;");
        signUpGrid.add(signUpVBox, 0, 0);
        return new Scene(signUpGrid, 1500, 1000);
    }

    private void handleSignUp(Stage primaryStage) {
        String username = signUpUsernameField.getText();
        String email = signUpEmailField.getText();
        String gender = signUpGenderChoice.getValue();
        String password = signUpPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || gender.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Username already exists.");
                return;
            }
        }

        User newUser = new User(username, email, gender, password);
        users.add(newUser);
        saveUsers();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Sign up successful.");
        primaryStage.setScene(loginScene);
    }

    private void handleLogin(Stage primaryStage) {
        String username = userNameField.getText();
        String password = passwordField.getText();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                primaryStage.setScene(homeScene);
                return;
            }
        }

        showAlert(Alert.AlertType.ERROR, "Error", "Invalid username or password.");
    }
////////////////////////////END OF SIGN UP SCENE///////////////////////////////////////////////////

    /////////////////////////////HOME SCENE FOR INVENTORY AND RATE LIST///////////////////////////////
    private Scene createHomeScene(Stage primaryStage) {
        loadProductsFromFile();
        BorderPane homePane = new BorderPane();
        homePane.setStyle("-fx-background-color: darkgreen;");

        VBox topContainer = new VBox();
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPadding(new Insets(20)); // Add some padding to give space around the text

        Text welcomeText = new Text("Welcome to the Fruit & Vegetable Shop");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeText.setFill(Color.WHITE);

        topContainer.getChildren().add(welcomeText);
        homePane.setTop(topContainer);

        GridPane buttonBox = new GridPane();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setHgap(20); // Horizontal gap between buttons
        buttonBox.setVgap(20); // Vertical gap between buttons

        // Buttons on the left
        Button inventoryButton = new Button("Inventory");
        inventoryButton.setPrefWidth(300);
        inventoryButton.setPrefHeight(150);
        inventoryButton.setOnAction(e -> {
            Scene inventoryScene = createInventoryScene(primaryStage); // Create inventory scene
            primaryStage.setScene(inventoryScene); // Set inventory scene to primary stage
        });

        inventoryButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        buttonBox.add(inventoryButton, 0, 0);

        // Rate List button
        Button purchaseButton = new Button("Purchase");
        purchaseButton.setPrefWidth(300);
        purchaseButton.setPrefHeight(150);
        purchaseButton.setOnAction(e -> primaryStage.setScene(createPurchaseScene(primaryStage)));
        purchaseButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        buttonBox.add(purchaseButton, 0, 1);


        salesButton = new Button("Sales (Total Income: Rs. " + totalIncome + ")");
        salesButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        salesButton.setPrefWidth(300);
        salesButton.setPrefHeight(150);
        buttonBox.add(salesButton, 1, 0);


        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(300);
        logoutButton.setPrefHeight(150);
        logoutButton.setOnAction(e -> primaryStage.setScene(loginScene));
        logoutButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        buttonBox.add(logoutButton, 1, 1);

        // Add buttonBox to the center of the BorderPane
        homePane.setCenter(buttonBox);

        return new Scene(homePane, 1500, 1000);
    }
    /////////////////////////////////INCOME /////////////////////////////////////////////////////////////
    private void updateTotalIncome(double amount) {
        totalIncome += amount;
        // Update the text of the sales button to display the updated total income
        salesButton.setText("Sales (Total Income: Rs. " + totalIncome + ")");
    }
    private void makePurchaseAndUpdateIncome(double amount) {
        updateTotalIncome(amount);
    }

    //////////////////////////////////INVENTORY SCENE///////////////////////////////////////////////////
    private Scene createInventoryScene(Stage primaryStage) {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: green;");
        tableView = new TableView<>();
        productList = FXCollections.observableArrayList(loadProductsFromFile());
        primaryStage.setTitle("Product Management");
        // Table
        tableView.setPrefHeight(300);

        TableColumn<Product, String> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));

        TableColumn<Product, String> productNameColumn = new TableColumn<>("Product Name");
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productNameColumn.setPrefWidth(200);

        TableColumn<Product, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Product, Integer> stockColumn = new TableColumn<>("Stock (kg)");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Product, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableView.getColumns().addAll(productIdColumn, productNameColumn, typeColumn, stockColumn, priceColumn, statusColumn, dateColumn);
        tableView.setItems(productList);
        // Form
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setVgap(10);
        form.setHgap(10);

        productIdField = new TextField();
        productIdField.setPromptText("Product ID");
        form.add(new Label("Product ID:"), 0, 0);
        form.add(productIdField, 1, 0);

        productNameField = new TextField();
        productNameField.setPromptText("Product Name");
        form.add(new Label("Product Name:"), 0, 1);
        form.add(productNameField, 1, 1);

        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Fruit", "Vegetable");
        typeComboBox.setPromptText("Choose Type...");
        form.add(new Label("Type:"), 0, 2);
        form.add(typeComboBox, 1, 2);

        stockField = new TextField();
        stockField.setPromptText("0");
        form.add(new Label("Stock:"), 2, 0);
        form.add(stockField, 3, 0);

        priceField = new TextField();
        priceField.setPromptText("0.0");
        form.add(new Label("Price (Rs.):"), 2, 1);
        form.add(priceField, 3, 1);

        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Available", "Unavailable");
        statusComboBox.setPromptText("Choose Status...");
        form.add(new Label("Status:"), 2, 2);
        form.add(statusComboBox, 3, 2);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        //Button importButton = new Button("Import");
        Button goback = new Button("Go back");
        goback.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, clearButton,  goback);

        root.setPadding(new Insets(10));
        root.getChildren().addAll(form, buttonBox, tableView);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        //return new Scene(root, 800, 600);

        goback.setOnAction(e -> primaryStage.setScene(homeScene));
        // Add button functionality
        addButton.setOnAction(e -> addProduct());

        // Update button functionality
        updateButton.setOnAction(e -> updateProduct());

        // Delete button functionality
        deleteButton.setOnAction(e -> {
            Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                productList.remove(selectedProduct);
                saveProductsToFile(productList);
            }
        });

        // Clear button functionality
        clearButton.setOnAction(e -> clearFields());


        /////////////////////////////SELECTION TABLE ROW FOR UPDATE, DELETE///////////////////////////////////
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                productIdField.setText(newSelection.getProductId());
                productNameField.setText(newSelection.getProductName());
                typeComboBox.setValue(newSelection.getType());
                stockField.setText(String.valueOf(newSelection.getStock()));
                priceField.setText(String.valueOf(newSelection.getPrice()));
                statusComboBox.setValue(newSelection.getStatus());
            }
        });

        return scene;
    }
    /////////////////////////////////ADD PRODUCT TO TABLE //////////////////////////////////
    private void addProduct() {
        String productId = productIdField.getText();
        String productName = productNameField.getText();
        String type = typeComboBox.getValue();
        int stock = Integer.parseInt(stockField.getText());
        double price = Double.parseDouble(priceField.getText());
        String status = statusComboBox.getValue();
        String date = LocalDate.now().toString();  // Add logic to get the current date or a date input

        Product product = new Product(productId, productName, type, stock, price, status, date);
        productList.add(product);
        saveProductsToFile(productList);
        clearFields();
    }
/////////////////END OF ADD FUNCTION/////////////////////////////////////////////

    /////////////////////////////UPDATE THE PRODUCT//////////////////////////////////////////
    private void updateProduct() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            selectedProduct.setProductId(productIdField.getText());
            selectedProduct.setProductName(productNameField.getText());
            selectedProduct.setType(typeComboBox.getValue());
            selectedProduct.setStock(Integer.parseInt(stockField.getText()));
            selectedProduct.setPrice(Double.parseDouble(priceField.getText()));
            selectedProduct.setStatus(statusComboBox.getValue());
            selectedProduct.setDate(LocalDate.now().toString());
            // Update date if necessary
            tableView.refresh();
            saveProductsToFile(productList);
            clearFields();
        }
    }
/////////////////////////END OF UPDATE///////////////////////////////////////////////

    /////////////////////////CLEARING FIELDS///////////////////////////////////////////
    private void clearFields() {
        productIdField.clear();
        productNameField.clear();
        typeComboBox.setValue(null);
        stockField.clear();
        priceField.clear();
        statusComboBox.setValue(null);
    }
////////////////////////END OF CLEARING FIELDS///////////////////////////////////////

    /////////////////////////////READING AND WRITING PRODUCTS FROM FILE/////////////////////////////////////////////////
    private List<Product> loadProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.ser"))) {
            products = (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

    private void saveProductsToFile(ObservableList<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.ser"))) {
            oos.writeObject(new ArrayList<>(products));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//////////////////////////////////END OF READING WRITING PRODUCTS FROM FILE/////////////////////////////

    ///////////////////////////PURCHASING SCENE/////////////////////////////////////////////////////
    private Scene createPurchaseScene(Stage primaryStage) {
        HBox mainLayout = new HBox(10);
        mainLayout.setPadding(new Insets(10));

        // Left side (Menu)
        GridPane menuGrid = createMenuGrid();
        loadProductsToMenuGrid(menuGrid); // Load products to menu grid
        ScrollPane menuScrollPane = new ScrollPane(menuGrid);
        menuScrollPane.setFitToWidth(true);
        menuScrollPane.setFitToHeight(true);
        menuScrollPane.setPrefWidth(1000); // Adjust the width to fit more items
        menuGrid.setStyle("-fx-background-color: green;");

        // Right side (Order Summary)
        VBox orderSummary = createOrderSummary(primaryStage);
        orderSummary.setStyle("-fx-background-color: green;");
        mainLayout.getChildren().addAll(menuScrollPane, orderSummary);

        Scene scene = new Scene(mainLayout, 1350, 800);
        System.out.println("Rate List Scene Created"); // Debug statement

        return scene;
    }

    private VBox createOrderSummary(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        //vbox.setPrefWidth(300); // Keep the width constant for order summary

        TableView<OrderItem> table = new TableView<>(orderData);
        table.setPrefWidth(300); // Set the preferred width of the table

        TableColumn<OrderItem, String> productNameCol = new TableColumn<>("Product Name");
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productNameCol.setPrefWidth(100); // Set the preferred width of the column

        TableColumn<OrderItem, Integer> quantityCol = new TableColumn<>("Quantity(kg)");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(100); // Set the preferred width of the column

        TableColumn<OrderItem, Double> priceCol = new TableColumn<>("Price (Rs.)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100); // Set the preferred width of the column

        table.getColumns().addAll(productNameCol, quantityCol, priceCol);

        Label amountLabel = new Label("Amount:");
        amountLabel.setTextFill(Color.WHITE);
        TextField amountField = new TextField();
        Label changeLabel = new Label("Change: Rs. 0.0");
        changeLabel.setTextFill(Color.WHITE);
        totalLabel = new Label("Total: Rs. 0.0");
        totalLabel.setTextFill(Color.WHITE);
        Button payButton = new Button("Pay");
        payButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        Button receiptButton = new Button("Receipt");
        receiptButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        Button goback = new Button("Go Back");
        goback.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        goback.setOnAction(e -> primaryStage.setScene(homeScene));
        receiptButton.setOnAction(e -> showReceiptWindow(table));
//////////////////REMOVE BUTTON////////////////////////////////////
        removeButton.setOnAction(e -> {
            orderData.clear();
            updateTotal(); // Update total amount
        });
// ///   /////////////PAY BUTTON//////////////////////////////////////////////////////////
        payButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            double total = orderData.stream().mapToDouble(OrderItem::getPrice).sum();
            Optional<ButtonType> resultOptional = showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Are you sure you want to proceed with the payment?");
            resultOptional.ifPresent(result -> {
                if (result == ButtonType.OK) {
                    if (amount >= total) {
                        updateTotalIncome(total);
                        boolean stockSufficient = true;
                        for (OrderItem item : orderData) {
                            Product product = getProductByName(item.getProductName());
                            if (product != null && item.getQuantity() > product.getStock()) {
                                stockSufficient = false;
                                showAlert(Alert.AlertType.ERROR, "Stock Error", "Requested for " + product.getProductName() + " exceeds available stock.");
                                break;
                            }
                        }
                        if (stockSufficient) {
                            changeLabel.setText("Change: Rs. " + (amount - total));

                            ///////// Deduct quantities from stock
                            for (OrderItem item : orderData) {
                                Product product = getProductByName(item.getProductName());
                                if (product != null) {
                                    product.setStock(product.getStock() - item.getQuantity());
                                    System.out.println("Updated stock for " + product.getProductName() + ": " + product.getStock()); // Debug statement
                                }
                            }

                            // Update the inventory interface
                            updateInventoryInterface();
                            updateTotal();// Update total amount after successful payment
                        }
                    } else {
                        changeLabel.setText("Insufficient amount");
                    }
                }
            });
        });
        // Add all UI components to the VBox
        vbox.getChildren().addAll(table,totalLabel, amountLabel, amountField, changeLabel, payButton, removeButton, receiptButton, goback);
        return vbox;
    }
    private Product getProductByName(String productName) {
        for (Product product : productList) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        return null; // Product not found
    }
    private void updateInventoryInterface() {
        tableView.refresh();
        saveProductsToFile(productList);
    }
    private GridPane createMenuGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //gridPane.setPadding(new Insets(10));
        return gridPane;
    }
    ////////////////////LOADING THE PRODUCTS FROM INVENTORY TO PURCHASE/////////////////////////////////
    private void loadProductsToMenuGrid(GridPane menuGrid) {
        List<Product> products = loadProductsFromFile(); // Load products from file or database

        // Clear existing items in the menu grid
        menuGrid.getChildren().clear();

        // Define the number of columns in the menu grid
        final int NUM_COLUMNS = 3;

        int rowIndex = 0;
        int colIndex = 0;

        for (Product product : products) {
            // Create a box for product
            VBox productBox = createProductBox(product);

            // Add the product box to the menu grid
            menuGrid.add(productBox, colIndex, rowIndex);

            // Update row and column indices
            colIndex++;
            if (colIndex == NUM_COLUMNS) {
                colIndex = 0;
                rowIndex++;
            }
        }
    }
    /////////////////////RECEIPT INTERFACE////////////////////////////////////////
    private void showReceiptWindow(TableView<OrderItem> table) {
        Customer customer = new Customer();
        Stage receiptStage = new Stage();
        receiptStage.setTitle("Receipt");

        VBox receiptVbox = new VBox(10);
        receiptVbox.setPadding(new Insets(10));

        Label customerIdLabel = new Label("Customer ID: " + customer.getCustomerId()); // Get customer ID from Customer object
        Label shopNameLabel = new Label("Fruit and Vegetable Stall");
        shopNameLabel.setAlignment(Pos.CENTER);
        shopNameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Label dateLabel = new Label("Date: " + dateFormatter.format(now));
        Label timeLabel = new Label("Time: " + timeFormatter.format(now));

        TableView<OrderItem> receiptTable = new TableView<>(FXCollections.observableArrayList(table.getItems()));
        TableColumn<OrderItem, String> receiptProductNameCol = new TableColumn<>("Product Name");
        receiptProductNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        receiptProductNameCol.setPrefWidth(150);

        TableColumn<OrderItem, String> receiptTypeCol = new TableColumn<>("Type");
        receiptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<OrderItem, Integer> receiptQuantityCol = new TableColumn<>("Quantity");
        receiptQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<OrderItem, Double> receiptPriceCol = new TableColumn<>("Price (Rs.)");
        receiptPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        receiptTable.getColumns().addAll(receiptProductNameCol, receiptTypeCol, receiptQuantityCol, receiptPriceCol);

        double total = table.getItems().stream().mapToDouble(OrderItem::getPrice).sum();
        Label receiptTotalLabel = new Label("Total: Rs. " + total);

        Label thankYouLabel = new Label("Thank you for visiting Fruit and Vegetable Stall!");
        thankYouLabel.setAlignment(Pos.CENTER);
        thankYouLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        receiptVbox.getChildren().addAll(customerIdLabel, shopNameLabel, dateLabel, timeLabel, receiptTable, receiptTotalLabel, thankYouLabel);

        Scene receiptScene = new Scene(receiptVbox, 600, 400);
        receiptStage.setScene(receiptScene);
        receiptStage.show();
    }
//////////////////////END OF RECEIPT ////////////////////////////////////////////////////

    ////////////////////////CREATING PRODUCT BOX IN MENU////////////////////////////////////////////////
    private VBox createProductBox(Product product) {
        // Create a VBox to hold the product information
        VBox productBox = new VBox(5);
        productBox.setPadding(new Insets(10));
        productBox.setAlignment(Pos.CENTER);
        productBox.setStyle("-fx-border-color: black; -fx-border-width: 3px;");

        // Add product name label
        Label nameLabel = new Label(product.getProductName());
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setAlignment(Pos.CENTER);
        productBox.getChildren().add(nameLabel);

        // Add product price label
        Label priceLabel = new Label("Price: " +" Rs"+ product.getPrice() );
        priceLabel.setTextFill(Color.WHITE);
        priceLabel.setAlignment(Pos.CENTER);
        productBox.getChildren().add(priceLabel);

        // Add quantity spinner
        Spinner<Integer> quantitySpinner = new Spinner<>(0, 100, 0);
        quantitySpinner.setEditable(true); // Allow manual input
        productBox.getChildren().add(quantitySpinner);

///////////////////////////ADD BUTTON OF BOX IN PURCHASE/////////////////////////////////////
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        addButton.setOnAction(e -> {
            int quantity = quantitySpinner.getValue();
            if (quantity > 0) {
                if (quantity <= product.getStock()) {
                    orderData.add(new OrderItem(product.getProductName(), quantity, product.getPrice() * quantity));
                    updateTotal(); // Update total amount
                } else {
                    showAlert(Alert.AlertType.ERROR, "Stock Error", "Quantity exceeds available stock.");
                }
            }
        });
        productBox.getChildren().add(addButton);
        return productBox;
    }
    private void updateTotal() {
        double total = 0;
        for (OrderItem item : orderData) {
            total += item.getPrice();
        }
        totalLabel.setText("Total: Rs. " + total);
    }

    private Optional<ButtonType> showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}