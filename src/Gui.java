import java.awt.*;
import javax.swing.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;

public class Gui {
    JFrame f1;
    JLabel l_welMessage, l_university, l_department, l_name, l_years;
    JTextField t_university, t_department, t_name;
    JComboBox<String> years;
    JButton b_confirm;
    JPanel p1;
    Settings s;
    GridBagConstraints gbc;
    // ! change name to selected semester index
    int k;      

    JFrame f2;
    JPanel pCenter, pWest, pTop, pEast, pCenter_inSemester, pCenter_inStats;
    JTabbedPane tp;
    JMenu menu;
    JMenuBar mb;
    JMenuItem i1, i2;
    JButton[] b_semesters, b_courses;
    JLabel[] l_semesters;
    JLabel l_title;
    JButton b_back, b_addCourse, b_showStats;
    // JButton b_deleteCourse;
    int numOfSemesters;
    String newCourse_grade;
    String newCourse_name, fileId, oldName;
    JLabel semesterMes, statsMes, l_stats;

    Gui(Semester[] semArray){
        s = new Settings();

        // f1
        f1 = new JFrame("Uni-Tracker");
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setLayout(new GridBagLayout());
        f1.setSize(550, 400);
        ImageIcon img = new ImageIcon("icon2.png");
        f1.setIconImage(img.getImage());

        // f2
        f2 = new JFrame("Uni-Tracker");
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.setLayout(new BorderLayout());
        f2.setIconImage(img.getImage());
        f2.setSize(700, 500);

        // * components of frame 1 ///////////////////////////////////////////////////////////////////////////////////////////////////

        //panel
        p1 = new JPanel();
        p1.setLayout(new GridBagLayout());

        // GridBagConstraints used when components are added
        gbc = new GridBagConstraints();

        // labels
        l_welMessage = new JLabel("<html><b><center><h3>Before you can use this App, you first have to provide the following information:</h3></center></b><html>");;
        l_name = new JLabel("Student's name and surname: ");
        l_name.setToolTipText("Enter your name as you want it to be displayed in the App");
        l_university = new JLabel("University: ");
        l_name.setToolTipText("Enter your University's as you want it to be displayed in the App");
        l_department = new JLabel("Department: ");
        l_department.setToolTipText("Enter your Department's as you want it to be displayed in the App");
        l_years = new JLabel("Studying years: ");
        l_years.setToolTipText("Enter your Department's length of curriculum");

        // textFields
        t_university = new JTextField(); 
        t_name = new JTextField(); 
        t_department = new JTextField();

        // comboBox
        String years_arr[]={"1", "2", "3", "4", "5"}; 
        years = new JComboBox<>(years_arr);    

        // Buttons
        b_confirm = new JButton("Confirm and continue");
        b_confirm.setToolTipText("Save all the data provided above and continue");

        // add l_welcome
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridwidth = 2;
        gbc.ipady = 40;
        gbc.ipadx = 10;
        p1.add(l_welMessage, gbc);
        gbc.ipady = 10;
        gbc.ipadx = 10;

        gbc.insets = new Insets(10,0,10,0);

        // add l_name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; 
        p1.add(l_name, gbc);

        // add t_name
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; 
        p1.add(t_name, gbc);

        // add l_university
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; 
        p1.add(l_university, gbc);

        // add t_university
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; 
        p1.add(t_university, gbc);

        // add l_department
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; 
        p1.add(l_department, gbc);

        // add t_department
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1; 
        p1.add(t_department, gbc);

        // add l_years
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1; 
        p1.add(l_years, gbc);

        // add years comboBox
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1; 
        p1.add(years, gbc);

        // add years comboBox
        gbc.insets = new Insets(20,60,20,60);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; 
        p1.add(b_confirm, gbc);

        // add frame and panel
        f1.add(p1, gbc);
        f1.setVisible(true);

        // actionListeners
        b_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String[] data = new String[4];
                boolean ok = true;
                data[0] = t_name.getText();
                data[1] = t_university.getText();
                data[2] = t_department.getText();
                data[3] = years.getSelectedItem().toString();
                for(int i=0 ; i<4 ; i++){
                    if (data[i].equals("")){
                        ok = false;
                        break;
                    }
                }

                if(ok){
                    int ans = JOptionPane.showConfirmDialog(f1, "Are you sure you want to save the information?");
                    if(ans == JOptionPane.YES_OPTION){  
                        f1.setVisible(false); // Set frame's visibility to false
                        f1.dispose(); // Destroy the frame
                        String[] newSettings = {"false", data[0], data[1], data[2], data[3]};
                        s.editSettings(newSettings);
                    }
                }

                else{
                    JOptionPane.showMessageDialog(f1,"You must fill in all the needed information", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }   
        });

        // * components of frame 2 ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // panels
        tp = new JTabbedPane();

        pCenter = new JPanel();
        pCenter.setLayout(new GridBagLayout());

        pWest = new JPanel();
        pWest.setLayout(new GridBagLayout());
        pWest.setBackground(Color.BLACK);

        pTop = new JPanel();
        pTop.setLayout(new GridBagLayout());
        pTop.setBackground(Color.BLACK);

        pEast = new JPanel();
        pEast.setLayout(new GridBagLayout());
        pEast.setBackground(Color.BLACK);


        // menuBer
        mb = new JMenuBar();  
        menu = new JMenu();
        i1=new JMenuItem("Item 1");
        i2=new JMenuItem("Item 2");
        menu.add(i1);
        menu.add(i2);
        JMenu subMenu=new JMenu("Sub Menu"); 
        menu.add(subMenu);  
        mb.add(menu);  
        f2.setJMenuBar(mb);        

        // l_title
        String name = s.getSettings()[1];
        String university = s.getSettings()[2];
        String department = s.getSettings()[3];
        String title = "<html><center><b><h2>" + university + "</b><br>" + department + "<br>"+ name + "</h2></center></html>";
        l_title = new JLabel(title);
        l_title.setForeground(Color.WHITE);
        pTop.add(l_title);

        // buttons and labels for the main menu
        numOfSemesters = Integer.parseInt(s.getSettings()[4]) * 2;
        b_semesters = new JButton[numOfSemesters];
        gbc.insets = new Insets(10, 10, 10, 10);
        int year = 1;
        for(int i=0 ; i<numOfSemesters ; i++){
            String text = "Semester " + (i+1);
            text = "<html><h4><b>" + text + "</b></h4></html>";
            b_semesters[i] = new JButton(text);
            if(i%2 == 0){
                JLabel lab = new JLabel("Year " + year);
                year++;
                gbc.ipadx = 40;
                gbc.ipady = 5;
                gbc.gridx = 0;
                gbc.gridy = i;
                gbc.gridwidth = 1;
                pCenter.add(lab, gbc);
                gbc.ipadx = 40;
                gbc.ipady = 5;
                gbc.gridx = 1;
                gbc.gridy = i;
                gbc.gridwidth = 1;
            }
            else{
                gbc.gridx = 2;
                gbc.gridy = i-1;
                gbc.gridwidth = 1;
            } 
            pCenter.add(b_semesters[i], gbc);
        }

        // add panels
        f2.add(tp, BorderLayout.CENTER);
        tp.add(pCenter, "Main menu");
        pWest.setBorder( BorderFactory.createEmptyBorder(0,0,0,100) );
        pEast.setBorder( BorderFactory.createEmptyBorder(0,0,0,100) );
        f2.add(pWest, BorderLayout.WEST);   
        f2.add(pEast, BorderLayout.EAST);   
        f2.add(pTop, BorderLayout.NORTH);   

        pCenter_inSemester = new JPanel();
        pCenter_inSemester.setLayout(new GridBagLayout());
        pCenter_inSemester.setBackground(Color.WHITE);
        tp.add(pCenter_inSemester, "Semester Grades");

        pCenter_inStats = new JPanel();
        pCenter_inStats.setLayout(new GridBagLayout());
        pCenter_inStats.setBackground(Color.WHITE);
        
        tp.add(pCenter_inStats, "Semester Grades");

        if(s.getSettings()[0].equals("false")){
            f1.setVisible(false);
            f1.dispose();
            f2.setVisible(true);
        }

        // fix changer


        statsMes = new JLabel("Select a semester from the main menu and then select Show Statistics");
        semesterMes = new JLabel("Please select a semester from the main menu to be displayed");

        tp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(tp.getSelectedIndex() == 0){
                    pCenter_inSemester.removeAll();
                    pCenter_inStats.removeAll();
                }
                else if(tp.getSelectedIndex() == 1){
                    pCenter_inStats.removeAll();
                    pCenter_inSemester.add(semesterMes, gbc);
                    semesterMes.setVisible(true);
                }
                else if(tp.getSelectedIndex() == 2){ 
                    pCenter_inSemester.removeAll();
                    pCenter_inStats.add(statsMes, gbc); 
                    statsMes.setVisible(true);
                }
            }
        });

        for(int i=0 ; i<numOfSemesters ; i++){
            b_semesters[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tp.setSelectedIndex(1);
                    semesterMes.setVisible(false);
                    JButton pressed = (JButton) e.getSource();
// System.out.println(pressed.getText());
                    k = Character.getNumericValue(pressed.getText().charAt(22)) - 1;
                    if(pressed.getText().charAt(23) == '0' ){
                        k = 9;
                    }
                    pCenter.setVisible(false);
                    gbc.fill = GridBagConstraints.NONE; 
                    gbc.ipadx = 0;
                    gbc.ipady = 0;

// System.out.println(k);
                    String semesterTitle = "<html><center><h2>Semester " + (k+1) + "</h2></center></html>";
                    JLabel l_SemesterTitle = new JLabel(semesterTitle); 
                    l_SemesterTitle.setForeground(Color.BLUE);
                    b_courses = new JButton[semArray[k].getNumberOfCourses()]; 
                    JLabel[] l_grades = new JLabel[semArray[k].getNumberOfCourses()];
                    int j = 0; 

                    gbc.gridwidth = 2;
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    pCenter_inSemester.add(l_SemesterTitle, gbc);

                    b_back = new JButton("Back to main menu");
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 2;
                    pCenter_inSemester.add(b_back, gbc);

                    if(semArray[k].getNumberOfCourses() == 0){
                        JLabel noCourseMessage = new JLabel("There are no courses in this semester yet...");
                        gbc.gridx = 0;
                        gbc.gridy = j+2;
                        gbc.gridwidth = 1;
                        pCenter_inSemester.add(noCourseMessage, gbc);
                        j++;
                    }
                    else{
                        gbc.gridwidth = 1;
                        gbc.ipady = 0;
                        gbc.ipadx = 0;
                        for(j=0 ; j<semArray[k].getNumberOfCourses() ; j++){
                            b_courses[j] = new JButton(semArray[k].getCourses()[j].getName());
                            b_courses[j].setName(semArray[k].getCourses()[j].getId());
                            b_courses[j].setBackground(Color.BLACK);
                            b_courses[j].setForeground(Color.WHITE);
                            l_grades[j] = new JLabel(Float.toString(semArray[k].getCourses()[j].getGrade()));
                            gbc.gridx = 0;
                            gbc.gridy = j+2;
                            pCenter_inSemester.add(b_courses[j], gbc);
                            gbc.gridx = 1;
                            gbc.gridy = j+2;
                            pCenter_inSemester.add(l_grades[j], gbc);
                            b_courses[j].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JButton pressed = (JButton) e.getSource();
                                    oldName = pressed.getText();
                                    fileId = pressed.getName();
                                    JFrame optionPane = new JFrame("Edit Course info");
                                    optionPane.setLayout(new GridBagLayout());

                                    optionPane.setSize(700, 300);
                                    optionPane.setResizable(false);
                                    JLabel l_newCourseName = new JLabel("Name of the Course: ");
                                    JLabel l_newCourseGrade = new JLabel("Grade of the Course: ");
                                    JTextField t_newCourseName= new JTextField(oldName);
                                    JTextField t_newCourseGrade= new JTextField();

                                    JPanel p = new JPanel();
                                    p.setLayout(new GridBagLayout());
                                    gbc.fill = GridBagConstraints.HORIZONTAL;
                                    gbc.ipadx = 200;
                                    gbc.ipady = 20;

                                    gbc.gridx = 0;
                                    gbc.gridy = 0;
                                    p.add(l_newCourseName, gbc);
                                    gbc.gridx = 1;
                                    gbc.gridy = 0;
                                    p.add(t_newCourseName, gbc);
                                    gbc.gridx = 0;
                                    gbc.gridy = 1;
                                    p.add(l_newCourseGrade, gbc);
                                    gbc.gridx = 1;
                                    gbc.gridy = 1;
                                    p.add(t_newCourseGrade, gbc);

                                    JButton b_ok = new JButton("Confirm");
                                    JButton b_cancel = new JButton("Cancel");
                                    gbc.gridx = 0;
                                    gbc.gridy = 2;
                                    p.add(b_ok, gbc);
                                    gbc.gridx = 1;
                                    gbc.gridy = 2;
                                    p.add(b_cancel, gbc);

                                    optionPane.add(p);
                                    optionPane.setVisible(true);
                                    
                                    b_ok.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            //save data
                                            newCourse_name = t_newCourseName.getText();
                                            newCourse_grade = t_newCourseGrade.getText();
                                            
                                            // open file and edit 
                                            Course.edit_csv(fileId, newCourse_name, newCourse_grade);

                                            optionPane.setVisible(false);
                                            optionPane.dispose();
                                            pCenter_inSemester.removeAll();
                                            b_semesters[k].doClick();

// ! change button and grade text

                                        }   
                                    });

                                    b_cancel.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            optionPane.setVisible(false);
                                            optionPane.dispose();
                                        }   
                                    });
                                }   
                            });
                        }    
                    }
                    
                    b_addCourse = new JButton("Add a new course to this semester");
                    gbc.gridwidth = 1;
                    gbc.gridx = 0;
                    gbc.gridy = j+2;
                    pCenter_inSemester.add(b_addCourse, gbc);

                    b_showStats = new JButton("Show Stats");
                    gbc.gridwidth = 1;
                    gbc.gridx = 1;
                    gbc.gridy = j+2;
                    pCenter_inSemester.add(b_showStats, gbc);

                    if(semArray[k].getNumberOfCourses() == 0){
                        b_showStats.setEnabled(false);
                    }

                    // ! create b_delete course
                    // b_deleteCourse = new JButton("Delete a course from this semester");
                    // b_deleteCourse.setEnabled(false);
                    // gbc.gridwidth = 1;
                    // gbc.gridx = 1;
                    // gbc.gridy = j+2;
                    // pCenter_inSemester.add(b_deleteCourse, gbc);

                    b_back.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tp.setSelectedIndex(0);
                            pCenter_inSemester.removeAll();
                            pCenter_inSemester.setVisible(false);
                            pCenter.setEnabled(true);
                            pCenter.setVisible(true);
                        }   
                    });

                    b_showStats.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tp.setSelectedIndex(2);

                            String toDisplay = "<html><center>";
                            semArray[k].createStats();
                            if(semArray[k].getStats() != null){
                                toDisplay += "Mean: " + semArray[k].getStats().getMean() + "<br>";
                                toDisplay += "Median: " + semArray[k].getStats().getMedian() + "<br>";
                                if(semArray[k].getStats().getVariance() < 0){
                                    toDisplay += "Variance: -" + "<br>";
                                    toDisplay += "Standard Deviation: -" + "<br>";
                                }
                                else{
                                    toDisplay += "Variance: " + semArray[k].getStats().getVariance() + "<br>";
                                    toDisplay += "Standard Deviation: " + semArray[k].getStats().getStd() + "<br>";
                                }
                                toDisplay += "Minimum: " + semArray[k].getStats().getMinimum() + "<br>";
                                toDisplay += "Maximum: " + semArray[k].getStats().getMaximum() + "<br>";
                                toDisplay += "Number of Passed Courses : " + (int)semArray[k].getStats().getN() + "<br>";
                                toDisplay += "</center></html>";
                            }
                            else{
                                toDisplay = "No Statistics available for this semester";
                            }
                            JLabel l_statsTitle = new JLabel("<html><center><h2>Semester's statistics</h2></center></html>");
                            gbc.gridx = 0;
                            gbc.gridy = 0;
                            pCenter_inStats.add(l_statsTitle, gbc);

                            l_stats = new JLabel(toDisplay);
                            gbc.gridx = 0;
                            gbc.gridy = 1;
                            pCenter_inStats.add(l_stats, gbc);
                            l_stats.setVisible(true);
                            statsMes.setVisible(false);
                        }   
                    });

                    b_addCourse.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            newCourse_grade = "-1";
                            newCourse_name = "";
                            JFrame optionPane = new JFrame("Add a new course");
                            optionPane.setLayout(new GridBagLayout());

                            optionPane.setSize(700, 300);
                            optionPane.setResizable(false);
                            JLabel l_newCourseName = new JLabel("Name of the Course: ");
                            JLabel l_newCourseGrade = new JLabel("Grade of the Course: ");
                            JTextField t_newCourseName= new JTextField();
                            JTextField t_newCourseGrade= new JTextField();

                            JPanel p = new JPanel();
                            p.setLayout(new GridBagLayout());
                            gbc.fill = GridBagConstraints.HORIZONTAL;
                            gbc.ipadx = 200;
                            gbc.ipady = 20;

                            gbc.gridx = 0;
                            gbc.gridy = 0;
                            p.add(l_newCourseName, gbc);
                            gbc.gridx = 1;
                            gbc.gridy = 0;
                            p.add(t_newCourseName, gbc);
                            gbc.gridx = 0;
                            gbc.gridy = 1;
                            p.add(l_newCourseGrade, gbc);
                            gbc.gridx = 1;
                            gbc.gridy = 1;
                            p.add(t_newCourseGrade, gbc);

                            JButton b_ok = new JButton("Confirm");
                            JButton b_cancel = new JButton("Cancel");
                            gbc.gridx = 0;
                            gbc.gridy = 2;
                            p.add(b_ok, gbc);
                            gbc.gridx = 1;
                            gbc.gridy = 2;
                            p.add(b_cancel, gbc);

                            optionPane.add(p);
                            optionPane.setVisible(true);
                            
                            b_ok.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //save data
                                    newCourse_name = t_newCourseName.getText();
                                    newCourse_grade = t_newCourseGrade.getText();
                                    semArray[k].addCourse(newCourse_name, semArray[k].getSerialNumber()+1, Float.parseFloat(newCourse_grade));

                                    optionPane.setVisible(false);
                                    optionPane.dispose();
                                    pCenter_inSemester.removeAll();
                                    b_semesters[k].doClick();
                                }   
                            });

                            b_cancel.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    optionPane.setVisible(false);
                                    optionPane.dispose();
                                }   
                            });
                        }   
                    });
                } 
            });
        }   
    }
}
