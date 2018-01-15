/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialportcomms;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Aijaz
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    SerialPort port;

    public GUI() {
        
        initComponents();
        initialise();

        // open and configure the port
        // enter into an infinite loop that reads from the port and updates the GUI
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backBtn = new javax.swing.JButton();
        leftBtn = new javax.swing.JButton();
        forwardBtn2 = new javax.swing.JButton();
        rightBtn = new javax.swing.JButton();
        portsComboBox = new javax.swing.JComboBox<>();
        connectionLbl = new javax.swing.JLabel();
        stopBtn = new javax.swing.JButton();
        contBtn = new javax.swing.JButton();
        corrBtn = new javax.swing.JButton();
        roomBtn1 = new javax.swing.JButton();
        leftBtn2 = new javax.swing.JButton();
        rightBtn2 = new javax.swing.JButton();
        turnbackBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        scanBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        exitCorridor = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        msgArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        stopArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        wallArea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        disArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        searchArea = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        navArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        clearBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        backBtn.setText("V");
        backBtn.setEnabled(false);
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        leftBtn.setText("<");
        leftBtn.setEnabled(false);
        leftBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftBtnActionPerformed(evt);
            }
        });

        forwardBtn2.setText("^");
        forwardBtn2.setEnabled(false);
        forwardBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardBtn2ActionPerformed(evt);
            }
        });

        rightBtn.setText(">");
        rightBtn.setEnabled(false);
        rightBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightBtnActionPerformed(evt);
            }
        });

        portsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portsComboBoxActionPerformed(evt);
            }
        });

        connectionLbl.setText("Select a port");

        stopBtn.setText("Stop (p)");
        stopBtn.setEnabled(false);
        stopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopBtnActionPerformed(evt);
            }
        });

        contBtn.setText("Continue (c)");
        contBtn.setToolTipText("");
        contBtn.setEnabled(false);
        contBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contBtnActionPerformed(evt);
            }
        });

        corrBtn.setText("Corridor");
        corrBtn.setEnabled(false);
        corrBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corrBtnActionPerformed(evt);
            }
        });

        roomBtn1.setText("Room");
        roomBtn1.setEnabled(false);
        roomBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomBtn1ActionPerformed(evt);
            }
        });

        leftBtn2.setText("Left");
        leftBtn2.setEnabled(false);
        leftBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftBtn2ActionPerformed(evt);
            }
        });

        rightBtn2.setText("Right");
        rightBtn2.setEnabled(false);
        rightBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightBtn2ActionPerformed(evt);
            }
        });

        turnbackBtn.setText("Turn Back (e)");
        turnbackBtn.setEnabled(false);
        turnbackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnbackBtnActionPerformed(evt);
            }
        });

        jLabel5.setText("w, a, s, d");

        scanBtn.setText("Scan");
        scanBtn.setEnabled(false);
        scanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanBtnActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel8.setText("Zumo Search and Rescue");

        exitCorridor.setText("Exit Corridor (z)");
        exitCorridor.setEnabled(false);
        exitCorridor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitCorridorActionPerformed(evt);
            }
        });

        jLabel1.setText("Message");

        msgArea.setColumns(20);
        msgArea.setRows(5);
        jScrollPane3.setViewportView(msgArea);

        jLabel4.setText("Search status");

        stopArea.setColumns(20);
        stopArea.setRows(5);
        jScrollPane6.setViewportView(stopArea);

        jLabel2.setText("Reflector Sensors Status");

        wallArea.setColumns(20);
        wallArea.setRows(5);
        jScrollPane2.setViewportView(wallArea);

        disArea.setColumns(20);
        disArea.setRows(5);
        jScrollPane1.setViewportView(disArea);

        jLabel3.setText("Distance");

        searchArea.setColumns(20);
        searchArea.setRows(5);
        jScrollPane4.setViewportView(searchArea);

        navArea.setColumns(20);
        navArea.setRows(5);
        jScrollPane5.setViewportView(navArea);

        jLabel6.setText("Navigation data");

        jLabel9.setText("Stopping?");

        clearBtn.setText("Clear");
        clearBtn.setEnabled(false);
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(214, 214, 214)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5)
                        .addGap(21, 21, 21))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(turnbackBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(scanBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(leftBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(roomBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(corrBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rightBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(portsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(connectionLbl))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(leftBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(stopBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(forwardBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rightBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(exitCorridor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(265, 265, 265))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(portsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(connectionLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(forwardBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(leftBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rightBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(exitCorridor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stopBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roomBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(corrBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(leftBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rightBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(turnbackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(29, 29, 29)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void initialise() {

        SerialPort ports[] = SerialPort.getCommPorts();
        //SerialPort port;
        for (int i = 0; i < ports.length; i++) {
            portsComboBox.addItem(i + ". " + ports[i].getSystemPortName());
        }
        portsComboBox.setSelectedIndex(-1);
        portsComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                //if(port.isOpen()) port.closePort();
                int chosenPort = portsComboBox.getSelectedIndex();
                forwardBtn2.setEnabled(true);
                backBtn.setEnabled(true);
                leftBtn.setEnabled(true);
                rightBtn.setEnabled(true);
                 leftBtn2.setEnabled(true);
                rightBtn2.setEnabled(true);
                stopBtn.setEnabled(true);
                turnbackBtn.setEnabled(true);
                exitCorridor.setEnabled(true);
                clearBtn.setEnabled(true);
                clearBtn.setEnabled(true);
                roomBtn1.setEnabled(true);
                corrBtn.setEnabled(true);
                contBtn.setEnabled(true);
                scanBtn.setEnabled(true);
                
                port = ports[chosenPort];
                port.setBaudRate(57600);
                if (port.openPort()) {
                    connectionLbl.setText("Successfully opened the port.");
                    port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

                    port.addDataListener(new SerialPortDataListener() {
                        @Override
                        public int getListeningEvents() {
                            return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                        }

                        @Override
                        public void serialEvent(SerialPortEvent event) {
                            if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                                return;
                            }

                            Scanner data = new Scanner(port.getInputStream());

                            while (data.hasNextLine()) {
                                String str = data.nextLine();
                                if (str.endsWith("cm")) {
                                    disArea.append(str + "\n");
                                    disArea.setCaretPosition(disArea.getDocument().getLength() - 1);
                                } else if (str.contains("FRONT WALL") || str.contains("LEFT WALL") || str.contains("RIGHT WALL")||str.contains("CORNER")) {
                                    wallArea.append(str + "\n");
                                    wallArea.setCaretPosition(wallArea.getDocument().getLength() - 1);
                                } else if (str.contains("Found Object")||str.contains("in Corridor No.")) {
                                    searchArea.append(str + "\n");
                                    searchArea.setCaretPosition(searchArea.getDocument().getLength() - 1);
                                } else if (str.contains("- Corridor No. ")) {
                                    navArea.append(str + "\n");
                                    navArea.setCaretPosition(navArea.getDocument().getLength() - 1);
                                } else if (str.contains("Going ") || str.contains("Press") ||str.contains("Exiting")||str.contains("Scanning")) {
                                    msgArea.append(str + "\n");
                                    msgArea.setCaretPosition(msgArea.getDocument().getLength() - 1);
                                }else if(str.contains("Stopping"))
                                {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                    Date date = new Date();
                                    stopArea.append(str+" "+date+"\n");
                                    stopArea.setCaretPosition(stopArea.getDocument().getLength()-1);    
                                }
                            }
                        }
                    });

                } else {
                    connectionLbl.setText("Unable to open the port.");
                }
            }
        });

    }

    public void send(char c) {
        byte[] writeBuffer = new byte[1024];
        writeBuffer[0] = (byte) c;
        if (port.isOpen()) {
            port.writeBytes(writeBuffer, 1);
        }
    }

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        send('s');
    }//GEN-LAST:event_backBtnActionPerformed

    private void leftBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftBtnActionPerformed
        send('a');
    }//GEN-LAST:event_leftBtnActionPerformed

    private void forwardBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardBtn2ActionPerformed

        send('w');
    }//GEN-LAST:event_forwardBtn2ActionPerformed

    private void rightBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightBtnActionPerformed
        send('d');
    }//GEN-LAST:event_rightBtnActionPerformed

    private void portsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portsComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portsComboBoxActionPerformed

    private void stopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopBtnActionPerformed
        send('p');

    }//GEN-LAST:event_stopBtnActionPerformed

    private void contBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contBtnActionPerformed
        send('c');
    }//GEN-LAST:event_contBtnActionPerformed

    private void leftBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftBtn2ActionPerformed
        send('l');
    }//GEN-LAST:event_leftBtn2ActionPerformed

    private void roomBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomBtn1ActionPerformed
        send('j');
    }//GEN-LAST:event_roomBtn1ActionPerformed

    private void corrBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corrBtnActionPerformed
        send('k');
    }//GEN-LAST:event_corrBtnActionPerformed

    private void rightBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightBtn2ActionPerformed
        send('r');
    }//GEN-LAST:event_rightBtn2ActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        wallArea.setText("");
        disArea.setText("");
        msgArea.setText("");
        searchArea.setText("");
        navArea.setText("");
        stopArea.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_clearBtnActionPerformed

    private void turnbackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnbackBtnActionPerformed
        // TODO add your handling code here:
        send('e');
    }//GEN-LAST:event_turnbackBtnActionPerformed

    private void scanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanBtnActionPerformed
            // TODO add your handling code here:
            send('x');
    }//GEN-LAST:event_scanBtnActionPerformed

    private void exitCorridorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitCorridorActionPerformed
        // TODO add your handling code here:
        send('z');
    }//GEN-LAST:event_exitCorridorActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_R) {
            send('r');
        }
        if (key == KeyEvent.VK_L) {
            send('l');
        }
        if (key == KeyEvent.VK_K) {
            send('k');
        }
        if (key == KeyEvent.VK_J) {
            send('j');
        }
        if (key == KeyEvent.VK_P) {
            send('p');
        }
        if (key == KeyEvent.VK_W) {
            send('s');
        }
        if (key == KeyEvent.VK_E) {
            send('e');
        }
        if (key == KeyEvent.VK_A) {
            send('a');
        }
        if (key == KeyEvent.VK_D) {
            send('d');
        }
    }

    /**
     * @param e
     * @param args the command line arguments
     */
    public static void main(String args[])  {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new GUI().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JLabel connectionLbl;
    private javax.swing.JButton contBtn;
    private javax.swing.JButton corrBtn;
    private javax.swing.JTextArea disArea;
    private javax.swing.JButton exitCorridor;
    private javax.swing.JButton forwardBtn2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton leftBtn;
    private javax.swing.JButton leftBtn2;
    private javax.swing.JTextArea msgArea;
    private javax.swing.JTextArea navArea;
    private javax.swing.JComboBox<String> portsComboBox;
    private javax.swing.JButton rightBtn;
    private javax.swing.JButton rightBtn2;
    private javax.swing.JButton roomBtn1;
    private javax.swing.JButton scanBtn;
    private javax.swing.JTextArea searchArea;
    private javax.swing.JTextArea stopArea;
    private javax.swing.JButton stopBtn;
    private javax.swing.JButton turnbackBtn;
    private javax.swing.JTextArea wallArea;
    // End of variables declaration//GEN-END:variables
}
