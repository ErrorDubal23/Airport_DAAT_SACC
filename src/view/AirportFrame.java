/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.FlightController;
import controller.LocationController;
import controller.PassengerController;
import controller.PlaneController;
import model.entities.Flight;
import model.entities.Location;
import model.entities.Passenger;
import model.entities.Plane;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.json.UploudJSONToFlightRepository;
import model.json.UploudJSONToLocationRepository;
import model.json.UploudJSONToPassengerRepository;
import model.json.UploudJSONToPlaneRepository;
import model.repositories.FlightRepository;
import model.repositories.LocationRepository;
import model.repositories.PassengerRepository;
import model.repositories.PlaneRepository;
import util.responses.Response;

/**
 *
 * @author edangulo
 */
public class AirportFrame extends javax.swing.JFrame {

    private int x, y;
    private final PassengerRepository passengerRepo;
    private final PlaneRepository planeRepo;
    private final LocationRepository locationRepo;
    private final FlightRepository flightRepo;
    private final PassengerController passengerController;
    private UploudJSONToFlightRepository uploudJSONToFlightRepository;
    private UploudJSONToLocationRepository uploudJSONToLocationRepository;
    private UploudJSONToPassengerRepository uploudJSONToPassengerRepository;
    private UploudJSONToPlaneRepository uploudJSONToPlaneRepository;

    public AirportFrame() {
        initComponents();
        this.passengerRepo = PassengerRepository.getInstance();
        this.planeRepo = PlaneRepository.getInstance();
        this.locationRepo = LocationRepository.getInstance();
        this.flightRepo = FlightRepository.getInstance();
        this.passengerController = new PassengerController();

        String pathLocation = "json/locations.json";
        String pathPassenger = "json/passengers.json";
        String pathPlane = "json/planes.json";
        String pathFlight = "json/flights.json";
        this.uploudJSONToLocationRepository = new UploudJSONToLocationRepository(pathLocation);
        this.uploudJSONToPassengerRepository = new UploudJSONToPassengerRepository(pathPassenger);
        this.uploudJSONToPlaneRepository = new UploudJSONToPlaneRepository(pathPlane);
        this.uploudJSONToFlightRepository = new UploudJSONToFlightRepository(pathFlight);
        uploudJSONToLocationRepository.uploud();
        uploudJSONToPassengerRepository.uploud();
        uploudJSONToPlaneRepository.uploud();
        uploudJSONToFlightRepository.uploud();

        //for(Plane plane: planeRepo.findAll()){
        //    System.out.println(plane);
        //}
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);

        this.generateMonths();
        this.generateDays();
        this.generateHours();
        this.generateMinutes();
        this.blockPanels();
    }

    private void clearPassengerFields() {
        registrationPassagerIdTextField.setText("");
        registrationFirstNameTextField.setText("");
        registrationLastNameTextField.setText("");
        registrationPassagerBirthYearTextField.setText("");
        registrationPassagerPhoneCodeTextField.setText("");
        registrationPassagerPhoneTextField.setText("");
        registrationPassagerCountryTextField.setText("");
        registrationPassagerBirthMounthComboBox.setSelectedIndex(0);
        registrationPassagerBirthDayComboBox.setSelectedIndex(0);
    }

    private void clearLocationFields() {
        registrationAirportIdTextField.setText("");
        registrationAirportNameTextField.setText("");
        registrationAirportCityTextField.setText("");
        registrationAirportCountryTextField.setText("");
        registrationAirportLatitudeTextField.setText("");
        registrationAirportLongitudeTextField.setText("");
    }

    private void clearPlaneFields() {
        registrationAirplaneIdTextField.setText("");
        registrationAirplaneBrandTextField.setText("");
        registrationAirplaneModelTextField.setText("");
        registrationAirplaneMaxCapacityTextField.setText("");
        registrationAirplaneAirlineTextField.setText("");
    }

    private void clearFlightFields() {
        registrationFlightIdTextField.setText("");
        registrationFlightDepartureDateTextField.setText("");
        registrationFlightPlaneComboBox.setSelectedIndex(0);
        registrationFlightDepartureLocationComboBox.setSelectedIndex(0);
        registrationFlightArrivalLocationComboBox.setSelectedIndex(0);
        registrationFlightScaleLocationComboBox.setSelectedIndex(0);
        registrationFlightDepartureMounthComboBox.setSelectedIndex(0);
        registrationFlightDepartureDayComboBox.setSelectedIndex(0);
        registrationFlightDepartureHourComboBox.setSelectedIndex(0);
        registrationFlightDepartureMinuteComboBox.setSelectedIndex(0);
        registrationFlightArrivalHourComboBox.setSelectedIndex(0);
        registrationFlightArrivalMinuteComboBox.setSelectedIndex(0);
        registrationFlightScaleHourComboBox.setSelectedIndex(0);
        registrationFlightScaleMinuteComboBox.setSelectedIndex(0);
    }

    private void clearAddPassengerToFlight() {
        updatePassagerBirthMountComboBox.setSelectedIndex(0);
    }

    private void clearUpdatePassenger() {
        updatePassagerIdTextField.setText("");
        updatePassagerFirstNameTextField.setText("");
        updatePassagerLastNameTextField.setText("");
        updatePassagerBirthYearTextField.setText("");
        updatePassagerPhoneCodeTextField.setText("");
        updatePassagerPhoneTextField.setText("");
        updatePassagerCountryTextField.setText("");
        updatePassagerBirthMountComboBox.setSelectedIndex(0);
        updatePassagerBirthDayComboBox.setSelectedIndex(0);

    }

    private void clearDelayFlight() {
        hoursDelayedComboBox.setSelectedIndex(0);
        minutesDelayedComboBox.setSelectedIndex(0);
    }

    private void blockPanels() {
        //9, 11
        for (int i = 1; i < menujTabbedPane.getTabCount(); i++) {
            if (i != 9 && i != 11) {
                menujTabbedPane.setEnabledAt(i, false);
            }
        }
    }

    private void generateMonths() {
        for (int i = 1; i < 13; i++) {
            registrationPassagerBirthMounthComboBox.addItem("" + i);
            registrationFlightDepartureMounthComboBox.addItem("" + i);
            updatePassagerBirthMountComboBox.addItem("" + i);
        }
    }

    private void generateDays() {
        for (int i = 1; i < 32; i++) {
            registrationPassagerBirthDayComboBox.addItem("" + i);
            registrationFlightDepartureDayComboBox.addItem("" + i);
            updatePassagerBirthDayComboBox.addItem("" + i);
        }
    }

    private void generateHours() {
        for (int i = 0; i < 24; i++) {
            registrationFlightDepartureHourComboBox.addItem("" + i);
            registrationFlightArrivalHourComboBox.addItem("" + i);
            registrationFlightScaleHourComboBox.addItem("" + i);
            hoursDelayedComboBox.addItem("" + i);
        }
    }

    private void generateMinutes() {
        for (int i = 0; i < 60; i++) {
            registrationFlightDepartureMinuteComboBox.addItem("" + i);
            registrationFlightArrivalMinuteComboBox.addItem("" + i);
            registrationFlightScaleMinuteComboBox.addItem("" + i);
            minutesDelayedComboBox.addItem("" + i);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new view.PanelRound();
        panelRound2 = new view.PanelRound();
        btnExit = new javax.swing.JButton();
        menujTabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        user = new javax.swing.JRadioButton();
        administrator = new javax.swing.JRadioButton();
        userSelectComboBox = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        registrationPassagerPhoneCodeTextField = new javax.swing.JTextField();
        registrationPassagerIdTextField = new javax.swing.JTextField();
        registrationPassagerBirthYearTextField = new javax.swing.JTextField();
        registrationPassagerCountryTextField = new javax.swing.JTextField();
        registrationPassagerPhoneTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        registrationLastNameTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        registrationPassagerBirthMounthComboBox = new javax.swing.JComboBox<>();
        registrationFirstNameTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        registrationPassagerBirthDayComboBox = new javax.swing.JComboBox<>();
        btnRegisterPassager = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        registrationAirplaneIdTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        registrationAirplaneBrandTextField = new javax.swing.JTextField();
        registrationAirplaneModelTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        registrationAirplaneMaxCapacityTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        registrationAirplaneAirlineTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnCreateAirplane = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        registrationAirportIdTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        registrationAirportNameTextField = new javax.swing.JTextField();
        registrationAirportCityTextField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        registrationAirportCountryTextField = new javax.swing.JTextField();
        registrationAirportLatitudeTextField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        registrationAirportLongitudeTextField = new javax.swing.JTextField();
        btnCreateAirport = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        registrationFlightIdTextField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        registrationFlightPlaneComboBox = new javax.swing.JComboBox<>();
        registrationFlightDepartureLocationComboBox = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        registrationFlightArrivalLocationComboBox = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        registrationFlightScaleLocationComboBox = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        registrationFlightDepartureDateTextField = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        registrationFlightDepartureMounthComboBox = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        registrationFlightDepartureDayComboBox = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        registrationFlightDepartureHourComboBox = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        registrationFlightDepartureMinuteComboBox = new javax.swing.JComboBox<>();
        registrationFlightArrivalHourComboBox = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        registrationFlightArrivalMinuteComboBox = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        registrationFlightScaleHourComboBox = new javax.swing.JComboBox<>();
        registrationFlightScaleMinuteComboBox = new javax.swing.JComboBox<>();
        btnCreateFlight = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        idPassagerAddedTextField = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        flightsComboBox = new javax.swing.JComboBox<>();
        btnAddPassagerToFlight = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        myFlightsjTable = new javax.swing.JTable();
        btnRefreshMyFlightsTable = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        passagersTable = new javax.swing.JTable();
        btnRefreshPassagersTable = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        flightsjTable = new javax.swing.JTable();
        btnRefreshFllightsTable = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnRefreshPlanesTable = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        planesjTable = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        locationsjTable = new javax.swing.JTable();
        btnRefreshLocationsTable = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        hoursDelayedComboBox = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        idFlightDelayedComboBox = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        minutesDelayedComboBox = new javax.swing.JComboBox<>();
        btnDelayFlight = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        updatePassagerIdTextField = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        updatePassagerFirstNameTextField = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        updatePassagerLastNameTextField = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        updatePassagerBirthYearTextField = new javax.swing.JTextField();
        updatePassagerBirthMountComboBox = new javax.swing.JComboBox<>();
        updatePassagerBirthDayComboBox = new javax.swing.JComboBox<>();
        updatePassagerPhoneTextField = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        updatePassagerPhoneCodeTextField = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        updatePassagerCountryTextField = new javax.swing.JTextField();
        btnUpdatePassagerInfo = new javax.swing.JButton();
        panelRound3 = new view.PanelRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelRound1.setRadius(40);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound2MouseDragged(evt);
            }
        });
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound2MousePressed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnExit.setText("X");
        btnExit.setBorderPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap(1083, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addComponent(btnExit)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        panelRound1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        menujTabbedPane.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        user.setText("User");
        user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userActionPerformed(evt);
            }
        });
        jPanel1.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, -1, -1));

        administrator.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        administrator.setText("Administrator");
        administrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administratorActionPerformed(evt);
            }
        });
        jPanel1.add(administrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 164, -1, -1));

        userSelectComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        userSelectComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select User" }));
        userSelectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userSelectComboBoxActionPerformed(evt);
            }
        });
        jPanel1.add(userSelectComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 130, -1));

        menujTabbedPane.addTab("Administration", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel1.setText("Country:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel2.setText("ID:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel3.setText("First Name:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel4.setText("Last Name:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel5.setText("Birthdate:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel6.setText("+");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 20, -1));

        registrationPassagerPhoneCodeTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(registrationPassagerPhoneCodeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 50, -1));

        registrationPassagerIdTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(registrationPassagerIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 130, -1));

        registrationPassagerBirthYearTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(registrationPassagerBirthYearTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 90, -1));

        registrationPassagerCountryTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(registrationPassagerCountryTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 130, -1));

        registrationPassagerPhoneTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(registrationPassagerPhoneTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 130, -1));

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel7.setText("Phone:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel8.setText("-");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 30, -1));

        registrationLastNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(registrationLastNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 130, -1));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel9.setText("-");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 30, -1));

        registrationPassagerBirthMounthComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationPassagerBirthMounthComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));
        jPanel2.add(registrationPassagerBirthMounthComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, -1, -1));

        registrationFirstNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(registrationFirstNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 130, -1));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel10.setText("-");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 30, -1));

        registrationPassagerBirthDayComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationPassagerBirthDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));
        jPanel2.add(registrationPassagerBirthDayComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, -1, -1));

        btnRegisterPassager.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnRegisterPassager.setText("Register");
        btnRegisterPassager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterPassagerActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegisterPassager, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, -1, -1));

        menujTabbedPane.addTab("Passenger registration", jPanel2);

        jPanel3.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel11.setText("ID:");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(53, 96, 21, 25);

        registrationAirplaneIdTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(registrationAirplaneIdTextField);
        registrationAirplaneIdTextField.setBounds(180, 93, 130, 35);

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel12.setText("Brand:");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(53, 157, 50, 25);

        registrationAirplaneBrandTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(registrationAirplaneBrandTextField);
        registrationAirplaneBrandTextField.setBounds(180, 154, 130, 35);

        registrationAirplaneModelTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(registrationAirplaneModelTextField);
        registrationAirplaneModelTextField.setBounds(180, 213, 130, 35);

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel13.setText("Model:");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(53, 216, 55, 25);

        registrationAirplaneMaxCapacityTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(registrationAirplaneMaxCapacityTextField);
        registrationAirplaneMaxCapacityTextField.setBounds(180, 273, 130, 35);

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel14.setText("Max Capacity:");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(53, 276, 110, 25);

        registrationAirplaneAirlineTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(registrationAirplaneAirlineTextField);
        registrationAirplaneAirlineTextField.setBounds(180, 333, 130, 35);

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel15.setText("Airline:");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(53, 336, 70, 25);

        btnCreateAirplane.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnCreateAirplane.setText("Create");
        btnCreateAirplane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAirplaneActionPerformed(evt);
            }
        });
        jPanel3.add(btnCreateAirplane);
        btnCreateAirplane.setBounds(490, 480, 120, 40);

        menujTabbedPane.addTab("Airplane registration", jPanel3);

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel16.setText("Airport ID:");

        registrationAirportIdTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel17.setText("Airport name:");

        registrationAirportNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        registrationAirportCityTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel18.setText("Airport city:");

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel19.setText("Airport country:");

        registrationAirportCountryTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        registrationAirportLatitudeTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel20.setText("Airport latitude:");

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel21.setText("Airport longitude:");

        registrationAirportLongitudeTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        btnCreateAirport.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnCreateAirport.setText("Create");
        btnCreateAirport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAirportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(registrationAirportLongitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registrationAirportIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registrationAirportNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registrationAirportCityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registrationAirportCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registrationAirportLatitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(btnCreateAirport, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(515, 515, 515))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel17)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel18)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel19)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel20))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(registrationAirportIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(registrationAirportNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(registrationAirportCityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(registrationAirportCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(registrationAirportLatitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(registrationAirportLongitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(btnCreateAirport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        menujTabbedPane.addTab("Location registration", jPanel13);

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel22.setText("ID:");

        registrationFlightIdTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel23.setText("Plane:");

        registrationFlightPlaneComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightPlaneComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Plane" }));

        registrationFlightDepartureLocationComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightDepartureLocationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel24.setText("Departure location:");

        registrationFlightArrivalLocationComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightArrivalLocationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel25.setText("Arrival location:");

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel26.setText("Scale location:");

        registrationFlightScaleLocationComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightScaleLocationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel27.setText("Duration:");

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel28.setText("Duration:");

        jLabel29.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel29.setText("Departure date:");

        registrationFlightDepartureDateTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel30.setText("-");

        registrationFlightDepartureMounthComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightDepartureMounthComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel31.setText("-");

        registrationFlightDepartureDayComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightDepartureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel32.setText("-");

        registrationFlightDepartureHourComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightDepartureHourComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel33.setText("-");

        registrationFlightDepartureMinuteComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightDepartureMinuteComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        registrationFlightArrivalHourComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightArrivalHourComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel34.setText("-");

        registrationFlightArrivalMinuteComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightArrivalMinuteComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel35.setText("-");

        registrationFlightScaleHourComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightScaleHourComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        registrationFlightScaleMinuteComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        registrationFlightScaleMinuteComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        btnCreateFlight.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnCreateFlight.setText("Create");
        btnCreateFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateFlightActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(registrationFlightScaleLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(registrationFlightArrivalLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(46, 46, 46)
                        .addComponent(registrationFlightDepartureLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(registrationFlightIdTextField)
                            .addComponent(registrationFlightPlaneComboBox, 0, 130, Short.MAX_VALUE))))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(registrationFlightDepartureDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(registrationFlightDepartureMounthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(registrationFlightDepartureDayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(registrationFlightDepartureHourComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(registrationFlightDepartureMinuteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(registrationFlightArrivalHourComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(registrationFlightArrivalMinuteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(registrationFlightScaleHourComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(registrationFlightScaleMinuteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreateFlight, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(530, 530, 530))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel22))
                    .addComponent(registrationFlightIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(registrationFlightPlaneComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registrationFlightDepartureHourComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(registrationFlightDepartureMinuteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24)
                                .addComponent(registrationFlightDepartureLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29))
                            .addComponent(registrationFlightDepartureDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registrationFlightDepartureMounthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(registrationFlightDepartureDayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel25)
                                .addComponent(registrationFlightArrivalLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28))
                            .addComponent(registrationFlightArrivalHourComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(registrationFlightArrivalMinuteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(registrationFlightScaleHourComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(registrationFlightScaleMinuteComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(registrationFlightScaleLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel27)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(btnCreateFlight, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        menujTabbedPane.addTab("Flight registration", jPanel4);

        idPassagerAddedTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idPassagerAddedTextField.setEnabled(false);

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel44.setText("ID:");

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel45.setText("Flight:");

        flightsComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Flight" }));

        btnAddPassagerToFlight.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnAddPassagerToFlight.setText("Add");
        btnAddPassagerToFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPassagerToFlightActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45))
                .addGap(79, 79, 79)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(flightsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idPassagerAddedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(881, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddPassagerToFlight, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(509, 509, 509))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel44))
                    .addComponent(idPassagerAddedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(flightsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                .addComponent(btnAddPassagerToFlight, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        menujTabbedPane.addTab("Add to flight", jPanel6);

        myFlightsjTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        myFlightsjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Departure Date", "Arrival Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(myFlightsjTable);

        btnRefreshMyFlightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnRefreshMyFlightsTable.setText("Refresh");
        btnRefreshMyFlightsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMyFlightsTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(350, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefreshMyFlightsTable)
                .addGap(527, 527, 527))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(btnRefreshMyFlightsTable)
                .addContainerGap())
        );

        menujTabbedPane.addTab("Show my flights", jPanel7);

        passagersTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        passagersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Birthdate", "Age", "Phone", "Country", "Num Flight"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(passagersTable);

        btnRefreshPassagersTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnRefreshPassagersTable.setText("Refresh");
        btnRefreshPassagersTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshPassagersTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(489, 489, 489)
                        .addComponent(btnRefreshPassagersTable))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRefreshPassagersTable)
                .addContainerGap())
        );

        menujTabbedPane.addTab("Show all passengers", jPanel8);

        flightsjTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        flightsjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Departure Airport ID", "Arrival Airport ID", "Scale Airport ID", "Departure Date", "Arrival Date", "Plane ID", "Number Passengers"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(flightsjTable);

        btnRefreshFllightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnRefreshFllightsTable.setText("Refresh");
        btnRefreshFllightsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshFllightsTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(521, 521, 521)
                        .addComponent(btnRefreshFllightsTable)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRefreshFllightsTable)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        menujTabbedPane.addTab("Show all flights", jPanel9);

        btnRefreshPlanesTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnRefreshPlanesTable.setText("Refresh");
        btnRefreshPlanesTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshPlanesTableActionPerformed(evt);
            }
        });

        planesjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Model", "Max Capacity", "Airline", "Number Flights"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(planesjTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(btnRefreshPlanesTable))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(248, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnRefreshPlanesTable)
                .addGap(17, 17, 17))
        );

        menujTabbedPane.addTab("Show all planes", jPanel10);

        locationsjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Airport ID", "Airport Name", "City", "Country"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(locationsjTable);

        btnRefreshLocationsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnRefreshLocationsTable.setText("Refresh");
        btnRefreshLocationsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshLocationsTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(btnRefreshLocationsTable))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnRefreshLocationsTable)
                .addGap(17, 17, 17))
        );

        menujTabbedPane.addTab("Show all locations", jPanel11);

        hoursDelayedComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        hoursDelayedComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel46.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel46.setText("Hours:");

        jLabel47.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel47.setText("ID:");

        idFlightDelayedComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        idFlightDelayedComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID" }));

        jLabel48.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel48.setText("Minutes:");

        minutesDelayedComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        minutesDelayedComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        btnDelayFlight.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnDelayFlight.setText("Delay");
        btnDelayFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelayFlightActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minutesDelayedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel46))
                        .addGap(79, 79, 79)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hoursDelayedComboBox, 0, 159, Short.MAX_VALUE)
                            .addComponent(idFlightDelayedComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(820, 820, 820))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDelayFlight)
                .addGap(531, 531, 531))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(idFlightDelayedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(hoursDelayedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(minutesDelayedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
                .addComponent(btnDelayFlight)
                .addGap(33, 33, 33))
        );

        menujTabbedPane.addTab("Delay flight", jPanel12);

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel36.setText("ID:");

        updatePassagerIdTextField.setEditable(false);
        updatePassagerIdTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updatePassagerIdTextField.setEnabled(false);

        jLabel37.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel37.setText("First Name:");

        updatePassagerFirstNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel38.setText("Last Name:");

        updatePassagerLastNameTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel39.setText("Birthdate:");

        updatePassagerBirthYearTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        updatePassagerBirthMountComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updatePassagerBirthMountComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        updatePassagerBirthDayComboBox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        updatePassagerBirthDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        updatePassagerPhoneTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel40.setText("-");

        updatePassagerPhoneCodeTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel41.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel41.setText("+");

        jLabel42.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel42.setText("Phone:");

        jLabel43.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel43.setText("Country:");

        updatePassagerCountryTextField.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        btnUpdatePassagerInfo.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnUpdatePassagerInfo.setText("Update");
        btnUpdatePassagerInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatePassagerInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addGap(108, 108, 108)
                                .addComponent(updatePassagerIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(41, 41, 41)
                                .addComponent(updatePassagerFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(43, 43, 43)
                                .addComponent(updatePassagerLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(55, 55, 55)
                                .addComponent(updatePassagerBirthYearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(updatePassagerBirthMountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(updatePassagerBirthDayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(updatePassagerPhoneCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(updatePassagerPhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addGap(63, 63, 63)
                                .addComponent(updatePassagerCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(507, 507, 507)
                        .addComponent(btnUpdatePassagerInfo)))
                .addContainerGap(611, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(updatePassagerIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(updatePassagerFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(updatePassagerLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(updatePassagerBirthYearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatePassagerBirthMountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatePassagerBirthDayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41)
                    .addComponent(updatePassagerPhoneCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(updatePassagerPhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(updatePassagerCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUpdatePassagerInfo)
                .addGap(113, 113, 113))
        );

        menujTabbedPane.addTab("Update info", jPanel5);

        panelRound1.add(menujTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 41, 1150, 620));

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        panelRound1.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 660, 1150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void panelRound2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_panelRound2MousePressed

    private void panelRound2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_panelRound2MouseDragged

    private void administratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administratorActionPerformed
        if (user.isSelected()) {
            user.setSelected(false);
            userSelectComboBox.setSelectedIndex(0);

        }
        for (int i = 1; i < menujTabbedPane.getTabCount(); i++) {
            menujTabbedPane.setEnabledAt(i, true); //Esto va ha ser false
        }
        menujTabbedPane.setEnabledAt(5, false); //Y aqui pongo los true
        menujTabbedPane.setEnabledAt(6, false);
    }//GEN-LAST:event_administratorActionPerformed

    private void userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userActionPerformed
        if (administrator.isSelected()) {
            administrator.setSelected(false);
        }
        for (int i = 1; i < menujTabbedPane.getTabCount(); i++) {

            menujTabbedPane.setEnabledAt(i, false);

        }
        menujTabbedPane.setEnabledAt(9, true);
        menujTabbedPane.setEnabledAt(5, true);
        menujTabbedPane.setEnabledAt(6, true);
        menujTabbedPane.setEnabledAt(7, true);
        menujTabbedPane.setEnabledAt(11, true);
    }//GEN-LAST:event_userActionPerformed

    private void btnRegisterPassagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterPassagerActionPerformed
        // Obtener datos sin trim
        String idStr = registrationPassagerIdTextField.getText();
        String firstname = registrationFirstNameTextField.getText();
        String lastname = registrationLastNameTextField.getText();
        String yearStr = registrationPassagerBirthYearTextField.getText();
        String monthStr = registrationPassagerBirthMounthComboBox.getItemAt(registrationPassagerBirthMounthComboBox.getSelectedIndex());
        String dayStr = registrationPassagerBirthDayComboBox.getItemAt(registrationPassagerBirthDayComboBox.getSelectedIndex());
        String phoneCodeStr = registrationPassagerPhoneCodeTextField.getText();
        String phoneStr = registrationPassagerPhoneTextField.getText();
        String country = registrationPassagerCountryTextField.getText();

        PassengerController controller = new PassengerController();
        Response response = controller.registerPassenger(
                idStr, firstname, lastname,
                yearStr, monthStr, dayStr,
                phoneCodeStr, phoneStr, country
        );

        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, "Pasajero registrado exitosamente");
            this.userSelectComboBox.addItem(idStr.trim());
            clearPassengerFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    response.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnRegisterPassagerActionPerformed

    private void btnCreateAirplaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAirplaneActionPerformed
        String id = registrationAirplaneIdTextField.getText();
        String brand = registrationAirplaneBrandTextField.getText();
        String model = registrationAirplaneModelTextField.getText();
        String maxCapacityStr = registrationAirplaneMaxCapacityTextField.getText();
        String airline = registrationAirplaneAirlineTextField.getText();

        PlaneController planeController = new PlaneController();
        Response response = planeController.registerPlane(id, brand, model, maxCapacityStr, airline);

        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, "Avión registrado exitosamente");
            if (registrationFlightPlaneComboBox != null) {
                registrationFlightPlaneComboBox.addItem(id.trim().toUpperCase());
            }
            clearPlaneFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    response.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCreateAirplaneActionPerformed

    private void btnCreateAirportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAirportActionPerformed
        try {
            // Obtener datos del formulario
            String id = registrationAirportIdTextField.getText().trim();
            String name = registrationAirportNameTextField.getText().trim();
            String city = registrationAirportCityTextField.getText().trim();
            String country = registrationAirportCountryTextField.getText().trim();
            String latitudeStr = registrationAirportLatitudeTextField.getText().trim();
            String longitudeStr = registrationAirportLongitudeTextField.getText().trim();

            // Usar el controlador para registrar la ubicación
            LocationController locationController = new LocationController();
            Response response = locationController.registerLocation(
                    id, name, city, country, latitudeStr, longitudeStr
            );

            if (response.isSuccess()) {
                // Registro exitoso
                JOptionPane.showMessageDialog(this, "Aeropuerto registrado exitosamente");

                // Agregar a los combobox
                this.registrationFlightDepartureLocationComboBox.addItem(id);
                this.registrationFlightArrivalLocationComboBox.addItem(id);
                this.registrationFlightScaleLocationComboBox.addItem(id);

                // Limpiar campos
                clearLocationFields();
            } else {
                // Mostrar error
                JOptionPane.showMessageDialog(this,
                        response.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCreateAirportActionPerformed

    private void btnCreateFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateFlightActionPerformed
        try {
            // Obtener datos
            String id = registrationFlightIdTextField.getText();
            String planeId = registrationFlightPlaneComboBox.getSelectedItem().toString();
            String departureLocationId = registrationFlightDepartureLocationComboBox.getSelectedItem().toString();
            String arrivalLocationId = registrationFlightArrivalLocationComboBox.getSelectedItem().toString();
            String scaleLocationId = registrationFlightScaleLocationComboBox.getSelectedItem().toString();

            // Manejar escala opcional
            String hoursScaleStr = "0";
            String minutesScaleStr = "0";

            if (!scaleLocationId.isEmpty()) {
                hoursScaleStr = registrationFlightScaleHourComboBox.getSelectedItem().toString();
                minutesScaleStr = registrationFlightScaleMinuteComboBox.getSelectedItem().toString();
            }

            // Resto de datos
            String year = registrationFlightDepartureDateTextField.getText();
            String month = registrationFlightDepartureMounthComboBox.getSelectedItem().toString();
            String day = registrationFlightDepartureDayComboBox.getSelectedItem().toString();
            String hour = registrationFlightDepartureHourComboBox.getSelectedItem().toString();
            String minute = registrationFlightDepartureMinuteComboBox.getSelectedItem().toString();
            String hoursArrival = registrationFlightArrivalHourComboBox.getSelectedItem().toString();
            String minutesArrival = registrationFlightArrivalMinuteComboBox.getSelectedItem().toString();

            // Usar el controlador
            FlightController controller = new FlightController();
            Response response = controller.registerFlight(
                    id, planeId, departureLocationId, arrivalLocationId, scaleLocationId,
                    year, month, day, hour, minute, hoursArrival, minutesArrival,
                    hoursScaleStr, minutesScaleStr
            );

            // Manejar respuesta
            if (response.isSuccess()) {
                JOptionPane.showMessageDialog(this, "Vuelo registrado exitosamente");
                this.flightsComboBox.addItem(id.trim().toUpperCase());
                this.idFlightDelayedComboBox.addItem(id.trim().toUpperCase());
                clearFlightFields();
            } else {
                JOptionPane.showMessageDialog(this,
                        response.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCreateFlightActionPerformed

    private void btnUpdatePassagerInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatePassagerInfoActionPerformed

        // Obtener datos del formulario
        String idStr = updatePassagerIdTextField.getText().trim();
        String firstname = updatePassagerFirstNameTextField.getText().trim();
        String lastname = updatePassagerLastNameTextField.getText().trim();
        String yearStr = updatePassagerBirthYearTextField.getText().trim();
        String monthStr = updatePassagerBirthMountComboBox.getItemAt(updatePassagerBirthMountComboBox.getSelectedIndex()).toString();
        String dayStr = updatePassagerBirthDayComboBox.getItemAt(updatePassagerBirthDayComboBox.getSelectedIndex()).toString();
        String phoneCodeStr = updatePassagerPhoneCodeTextField.getText().trim();
        String phoneStr = updatePassagerPhoneTextField.getText().trim();
        String country = updatePassagerCountryTextField.getText().trim();

        // Validar campos vacíos
        if (idStr.isEmpty() || firstname.isEmpty() || lastname.isEmpty()
                || yearStr.isEmpty() || phoneCodeStr.isEmpty() || phoneStr.isEmpty() || country.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Usar el controlador para actualizar el pasajero
        PassengerController passengerController = new PassengerController();
        Response response = passengerController.updatePassenger(
                idStr, firstname, lastname, yearStr, monthStr, dayStr,
                phoneCodeStr, phoneStr, country
        );

        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this,
                    "Información actualizada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            clearUpdatePassenger();
        } else {
            JOptionPane.showMessageDialog(this,
                    response.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnUpdatePassagerInfoActionPerformed

    private void btnAddPassagerToFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPassagerToFlightActionPerformed
        String flightId = flightsComboBox.getSelectedItem().toString();
        String passengerId = idPassagerAddedTextField.getText();

        FlightController controller = new FlightController();
        Response response = controller.addPassengerToFlight(passengerId, flightId);

        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, "Pasajero añadido al vuelo exitosamente");
            clearAddPassengerToFlight();
        } else {
            JOptionPane.showMessageDialog(this,
                    response.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddPassagerToFlightActionPerformed

    private void btnDelayFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelayFlightActionPerformed
        try {
            // Obtener datos del formulario
            String flightId = idFlightDelayedComboBox.getSelectedItem() != null
                    ? idFlightDelayedComboBox.getSelectedItem().toString() : "";
            String hoursStr = hoursDelayedComboBox.getSelectedItem() != null
                    ? hoursDelayedComboBox.getSelectedItem().toString() : "";
            String minutesStr = minutesDelayedComboBox.getSelectedItem() != null
                    ? minutesDelayedComboBox.getSelectedItem().toString() : "";

            // Usar el controlador
            FlightController controller = new FlightController();
            Response response = controller.delayFlight(flightId, hoursStr, minutesStr);

            if (response.isSuccess()) {
                JOptionPane.showMessageDialog(this,
                        "Vuelo retrasado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                // Actualizar la tabla de vuelos
                btnRefreshFllightsTableActionPerformed(null);

                clearDelayFlight();
            } else {
                JOptionPane.showMessageDialog(this,
                        response.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDelayFlightActionPerformed

    private void btnRefreshMyFlightsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMyFlightsTableActionPerformed
        try {
            // Obtener el ID del pasajero seleccionado
            String passengerIdStr = userSelectComboBox.getItemAt(userSelectComboBox.getSelectedIndex()).toString();

            // Validar selección
            if (passengerIdStr.isEmpty() || passengerIdStr.equals(userSelectComboBox.getItemAt(0))) {
                JOptionPane.showMessageDialog(this,
                        "Por favor seleccione un pasajero válido",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Usar el controlador para obtener los vuelos del pasajero
            PassengerController passengerController = new PassengerController();
            Response response = passengerController.getPassengerById(passengerIdStr);

            if (response.isSuccess()) {
                Passenger passenger = (Passenger) response.getData();
                DefaultTableModel model = (DefaultTableModel) myFlightsjTable.getModel();
                model.setRowCount(0); // Limpiar tabla existente

                // Obtener vuelos ordenados por fecha de salida
                List<Flight> flights = passenger.getFlights().stream()
                        .sorted(Comparator.comparing(Flight::getDepartureDate))
                        .collect(Collectors.toList());

                // Llenar la tabla
                for (Flight flight : flights) {
                    model.addRow(new Object[]{
                        flight.getId(),
                        flight.getDepartureDate(),
                        flight.calculateArrivalDate()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        response.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "ID de pasajero inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar vuelos del pasajero: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRefreshMyFlightsTableActionPerformed

    private void btnRefreshPassagersTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshPassagersTableActionPerformed
        try {
            DefaultTableModel model = (DefaultTableModel) passagersTable.getModel();
            model.setRowCount(0);

            PassengerController controller = new PassengerController();
            Response response = controller.getAllPassengers();

            if (response.isSuccess()) {
                List<Passenger> passengers = (List<Passenger>) response.getData();
                for (Passenger passenger : passengers) {
                    model.addRow(new Object[]{
                        passenger.getId(),
                        passenger.getFullname(),
                        passenger.getBirthDate(),
                        passenger.calculateAge(),
                        passenger.generateFullPhone(),
                        passenger.getCountry(),
                        passenger.getFlights().size()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        response.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar pasajeros: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRefreshPassagersTableActionPerformed

    private void btnRefreshFllightsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshFllightsTableActionPerformed
        try {
            DefaultTableModel model = (DefaultTableModel) flightsjTable.getModel();
            model.setRowCount(0);

            FlightController controller = new FlightController();
            Response response = controller.getAllFlights();

            if (response.isSuccess()) {
                List<Flight> flights = (List<Flight>) response.getData();

                for (Flight flight : flights) {
                    model.addRow(new Object[]{
                        flight.getId(),
                        flight.getDepartureLocation().getAirportId(),
                        flight.getArrivalLocation().getAirportId(),
                        (flight.getScaleLocation() != null ? flight.getScaleLocation().getAirportId() : "-"),
                        formatDateTime(flight.getDepartureDate()),
                        formatDateTime(flight.calculateArrivalDate()),
                        flight.getPlane().getId(),
                        flight.getNumPassengers() + "/" + flight.getPlane().getMaxCapacity()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        response.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar vuelos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRefreshFllightsTableActionPerformed

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

    private void btnRefreshPlanesTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshPlanesTableActionPerformed
        DefaultTableModel model = (DefaultTableModel) planesjTable.getModel();
        model.setRowCount(0);

        PlaneController planeController = new PlaneController();
        Response response = planeController.getAllPlanes();

        if (response.isSuccess()) {
            List<Plane> planes = (List<Plane>) response.getData();
            for (Plane plane : planes) {
                model.addRow(new Object[]{
                    plane.getId(),
                    plane.getBrand(),
                    plane.getModel(),
                    plane.getMaxCapacity(),
                    plane.getAirline(),
                    plane.getNumFlights()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    response.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnRefreshPlanesTableActionPerformed

    private void btnRefreshLocationsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshLocationsTableActionPerformed
        DefaultTableModel model = (DefaultTableModel) locationsjTable.getModel();
        model.setRowCount(0);

        LocationController controller = new LocationController();
        Response response = controller.getAllLocations();

        if (response.isSuccess()) {
            System.out.println("Paso por aqui");
            List<Location> locations = (List<Location>) response.getData();
            for (Location location : locations) {
                model.addRow(new Object[]{
                    location.getAirportId(),
                    location.getAirportName(),
                    location.getAirportCity(),
                    location.getAirportCountry()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    response.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRefreshLocationsTableActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void userSelectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userSelectComboBoxActionPerformed
        try {
            String id = userSelectComboBox.getSelectedItem().toString();
            if (!id.equals(userSelectComboBox.getItemAt(0))) {
                updatePassagerIdTextField.setText(id);
                idPassagerAddedTextField.setText(id);
            } else {
                updatePassagerIdTextField.setText("");
                idPassagerAddedTextField.setText("");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_userSelectComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton administrator;
    private javax.swing.JButton btnAddPassagerToFlight;
    private javax.swing.JButton btnCreateAirplane;
    private javax.swing.JButton btnCreateAirport;
    private javax.swing.JButton btnCreateFlight;
    private javax.swing.JButton btnDelayFlight;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnRefreshFllightsTable;
    private javax.swing.JButton btnRefreshLocationsTable;
    private javax.swing.JButton btnRefreshMyFlightsTable;
    private javax.swing.JButton btnRefreshPassagersTable;
    private javax.swing.JButton btnRefreshPlanesTable;
    private javax.swing.JButton btnRegisterPassager;
    private javax.swing.JButton btnUpdatePassagerInfo;
    private javax.swing.JComboBox<String> flightsComboBox;
    private javax.swing.JTable flightsjTable;
    private javax.swing.JComboBox<String> hoursDelayedComboBox;
    private javax.swing.JComboBox<String> idFlightDelayedComboBox;
    private javax.swing.JTextField idPassagerAddedTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable locationsjTable;
    private javax.swing.JTabbedPane menujTabbedPane;
    private javax.swing.JComboBox<String> minutesDelayedComboBox;
    private javax.swing.JTable myFlightsjTable;
    private view.PanelRound panelRound1;
    private view.PanelRound panelRound2;
    private view.PanelRound panelRound3;
    private javax.swing.JTable passagersTable;
    private javax.swing.JTable planesjTable;
    private javax.swing.JTextField registrationAirplaneAirlineTextField;
    private javax.swing.JTextField registrationAirplaneBrandTextField;
    private javax.swing.JTextField registrationAirplaneIdTextField;
    private javax.swing.JTextField registrationAirplaneMaxCapacityTextField;
    private javax.swing.JTextField registrationAirplaneModelTextField;
    private javax.swing.JTextField registrationAirportCityTextField;
    private javax.swing.JTextField registrationAirportCountryTextField;
    private javax.swing.JTextField registrationAirportIdTextField;
    private javax.swing.JTextField registrationAirportLatitudeTextField;
    private javax.swing.JTextField registrationAirportLongitudeTextField;
    private javax.swing.JTextField registrationAirportNameTextField;
    private javax.swing.JTextField registrationFirstNameTextField;
    private javax.swing.JComboBox<String> registrationFlightArrivalHourComboBox;
    private javax.swing.JComboBox<String> registrationFlightArrivalLocationComboBox;
    private javax.swing.JComboBox<String> registrationFlightArrivalMinuteComboBox;
    private javax.swing.JTextField registrationFlightDepartureDateTextField;
    private javax.swing.JComboBox<String> registrationFlightDepartureDayComboBox;
    private javax.swing.JComboBox<String> registrationFlightDepartureHourComboBox;
    private javax.swing.JComboBox<String> registrationFlightDepartureLocationComboBox;
    private javax.swing.JComboBox<String> registrationFlightDepartureMinuteComboBox;
    private javax.swing.JComboBox<String> registrationFlightDepartureMounthComboBox;
    private javax.swing.JTextField registrationFlightIdTextField;
    private javax.swing.JComboBox<String> registrationFlightPlaneComboBox;
    private javax.swing.JComboBox<String> registrationFlightScaleHourComboBox;
    private javax.swing.JComboBox<String> registrationFlightScaleLocationComboBox;
    private javax.swing.JComboBox<String> registrationFlightScaleMinuteComboBox;
    private javax.swing.JTextField registrationLastNameTextField;
    private javax.swing.JComboBox<String> registrationPassagerBirthDayComboBox;
    private javax.swing.JComboBox<String> registrationPassagerBirthMounthComboBox;
    private javax.swing.JTextField registrationPassagerBirthYearTextField;
    private javax.swing.JTextField registrationPassagerCountryTextField;
    private javax.swing.JTextField registrationPassagerIdTextField;
    private javax.swing.JTextField registrationPassagerPhoneCodeTextField;
    private javax.swing.JTextField registrationPassagerPhoneTextField;
    private javax.swing.JComboBox<String> updatePassagerBirthDayComboBox;
    private javax.swing.JComboBox<String> updatePassagerBirthMountComboBox;
    private javax.swing.JTextField updatePassagerBirthYearTextField;
    private javax.swing.JTextField updatePassagerCountryTextField;
    private javax.swing.JTextField updatePassagerFirstNameTextField;
    private javax.swing.JTextField updatePassagerIdTextField;
    private javax.swing.JTextField updatePassagerLastNameTextField;
    private javax.swing.JTextField updatePassagerPhoneCodeTextField;
    private javax.swing.JTextField updatePassagerPhoneTextField;
    private javax.swing.JRadioButton user;
    private javax.swing.JComboBox<String> userSelectComboBox;
    // End of variables declaration//GEN-END:variables
}
