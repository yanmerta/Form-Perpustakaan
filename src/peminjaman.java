
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class peminjaman extends javax.swing.JFrame {
private Statement st;
private Connection con;
private ResultSet rsbuku;
private ResultSet rsuser;
private ResultSet rsanggota;
private ResultSet rspinjam;
private String tanggal="";
private String sql="";
String nopem,kode,idus,namaus,judul,idang,namaang;
int jmlpin,totpin;

    /**
     * Creates new form peminjaman
     */
    public peminjaman() {
        initComponents();
        koneksiPeminjaman();
        tampilPeminjaman("select * from tb_peminjaman");
        Pilihanuser();
        PilihBuku();
        PilihAnggota();
        waktu();
    }
    
    private void koneksiPeminjaman(){
      try {
          Class.forName("com.mysql.jdbc.Driver");
         con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_perpustakaan","root","");
          System.out.println("Hore Koneksi Berhasil !!!");
          JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
      } catch (Exception e) {
          System.out.println("Gagal Koneksi"+e);
      }
  }

    private void tampilPeminjaman(String sql){
      DefaultTableModel grid =new DefaultTableModel();
      grid.addColumn("No");
      grid.addColumn("No Pinjam");
      grid.addColumn("Tgl Pinjam");
      grid.addColumn("Id user");
      grid.addColumn("id anggota");
      grid.addColumn("Total pinjam");
      try {
            int i=1;
            st=con.createStatement();
            rspinjam=st.executeQuery("select * from tb_peminjaman");
            while (rspinjam.next()){
                grid.addRow(new Object[]{(""+i++), 
                    rspinjam.getString(1),rspinjam.getString(2),rspinjam.getString(3),
                    rspinjam.getString(4),rspinjam.getString(5)});
                table_peminjaman.setModel(grid);
                table_peminjaman.enable(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null , "Gagal Tampil " +e);
        }
  }
     private void kosongkan(){
        txtnopin.setText("");
        txtjudul.setText("");
        txtnamaang.setText("");
        txtnamaus.setText("");
        txthakakses.setText("");
        txtpenerbit.setText("");
        txtpenulis.setText("");
        txtjml_pinjam.setText("");
        cmbidanggota.setSelectedItem(null);
        cmbiduser.setSelectedItem(null);
        cmbkode.setSelectedItem(null);
        txtnopin.requestFocus();
    }
     
      private void Pilihanuser (){
       cmbiduser.removeAllItems();
       cmbiduser.addItem("Pilih");
       try {
          String sql ="select * from tb_user";
          Statement st=con.createStatement();
          rsuser=st.executeQuery(sql);
           while (rsuser.next()) {
               String IdUser =rsuser.getString("id_user");
               cmbiduser.addItem(IdUser);
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Gagal Menampilkan Id User \n" +e.getMessage());
       }
   }
      
       private void PilihBuku (){
       cmbkode.removeAllItems();
       cmbkode.addItem("Pilih");
       try {
          String sql ="select * from tb_buku";
          Statement st=con.createStatement();
          rsbuku=st.executeQuery(sql);
           while (rsbuku.next()) { 
               String buku =rsbuku.getString("kode_buku");
               cmbkode.addItem(buku);
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Gagal Menampilkan Kode Buku \n" +e.getMessage());
       }
   }
     private void PilihAnggota (){
       cmbidanggota.removeAllItems();
       cmbidanggota.addItem("Pilih");
       try {
          String sql ="select * from tb_anggota";
          Statement st=con.createStatement();
          rsanggota=st.executeQuery(sql);
           while (rsanggota.next()) {
               String anggota =rsanggota.getString("id_anggota");
               cmbidanggota.addItem(anggota);
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Gagal Menampilkan Id Anggota \n" +e.getMessage());
       }
   }

private void prosestambah(){
        try {
            DefaultTableModel tableModel = (DefaultTableModel)detail_peminjaman.getModel();
            String[]data = new String[5];
            data[0]=String.valueOf(cmbkode.getSelectedItem());
            data[1]=txtjudul.getText();
            data[2]=txtpenulis.getText();
            data[3]=txtpenerbit.getText();
            data[4]=txtjml_pinjam.getText();
            tableModel.addRow(data);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Eror Menampilkan Data.." +e.getMessage());
        }
    }
 
 private void total_pinjam(){
        int jumlahBaris = detail_peminjaman.getRowCount();
        int Jmlpin=1, totpin=1;

        
        TableModel tblmodel;
        tblmodel = detail_peminjaman.getModel();
        for (int i=0; i<jumlahBaris; i++){
                totpin=Jmlpin;
                jmlpin=totpin+i;
        }
        txtjml_pinjam.setText(String.valueOf(totpin));
        txttot_pinjam.setText(String.valueOf(jmlpin));
    }

  public void waktu(){
        Date tgl=new Date();
        DCtanggal.setDate(tgl);
    }
  
  private void simpandetail_peminjaman(){
        int jumlah_baris=detail_peminjaman.getRowCount();
        if (jumlah_baris == 0) {
            JOptionPane.showMessageDialog(rootPane, "Table Masih Kosong..!!");
        } else {
            try {
                int i=0;
                while(i<jumlah_baris){
                    st.executeUpdate("insert into detail_pinjam"
                            + "(no_peminjaman,kode_buku,jumlah_pinjam)"
                            + "values('"+txtnopin.getText()+"',"
                            + "'"+detail_peminjaman.getValueAt(i,0)+"',"
                            + "'"+txtjml_pinjam.getText()+"')");
                    i++;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan..!!"+e);
            }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtnopin = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtnamaus = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtnamaang = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cmbiduser = new javax.swing.JComboBox<>();
        cmbidanggota = new javax.swing.JComboBox<>();
        txtjml_pinjam = new javax.swing.JTextField();
        txthakakses = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtjudul = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbkode = new javax.swing.JComboBox<>();
        txtpenerbit = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btntambahbuku = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtpenulis = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_peminjaman = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        detail_peminjaman = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        txttot_pinjam = new javax.swing.JTextField();
        btnsimpan = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Perpustakaan Bersama");

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Transaksi Peminjaman Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(13, 13, 13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(153, 255, 153));
        jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel11.setText("No Peminjaman");

        jLabel12.setText("Tanggal Pinjam");

        txtnopin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnopinKeyPressed(evt);
            }
        });

        jLabel15.setText("Id User");

        jLabel16.setText("Nama User");

        jLabel17.setText("Id Anggota");

        txtnamaang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaangActionPerformed(evt);
            }
        });

        jLabel18.setText("Nama Anggota");

        cmbiduser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbiduserItemStateChanged(evt);
            }
        });

        cmbidanggota.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbidanggotaItemStateChanged(evt);
            }
        });

        jLabel20.setText("Hak akses");

        jLabel14.setText("Judul Buku");

        jLabel13.setText("Kode Buku");

        cmbkode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbkodeItemStateChanged(evt);
            }
        });

        jLabel21.setText("Penerbit");

        jLabel22.setText("Jumlah Pinjam");

        btntambahbuku.setText("+ Buku");
        btntambahbuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahbukuActionPerformed(evt);
            }
        });

        jLabel23.setText("Penulis");

        jPanel2.setBackground(new java.awt.Color(255, 204, 255));

        jLabel3.setText("Catatan :");

        jLabel4.setText("Hanya boleh meminjam maksimal 3 buku ");

        jLabel5.setText("dalam 1 kode transaksi");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txthakakses, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbidanggota, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(66, 66, 66)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel22))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtjml_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel23)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtnamaus, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbiduser, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtpenulis, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbkode, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtjudul, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel20)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtnamaang, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtnopin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btntambahbuku)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtnopin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(cmbiduser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(txtnamaus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(cmbkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(txtjudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(txtpenulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txthakakses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(cmbidanggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(txtpenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtnamaang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txtjml_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntambahbuku))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table_peminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table_peminjaman);

        detail_peminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Buku", "Judul", "Penulis", "Penerbit", "Jml  Pinjam"
            }
        ));
        jScrollPane2.setViewportView(detail_peminjaman);

        jLabel19.setText("Total Pinjam");

        btnsimpan.setBackground(new java.awt.Color(255, 255, 255));
        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnhapus.setBackground(new java.awt.Color(255, 255, 255));
        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnbatal.setBackground(new java.awt.Color(255, 255, 255));
        btnbatal.setText("Batal");
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });

        btnkeluar.setBackground(new java.awt.Color(255, 255, 255));
        btnkeluar.setText("Keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnsimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnhapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnkeluar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txttot_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnsimpan)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnbatal)
                                .addComponent(btnkeluar)
                                .addComponent(btnhapus)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txttot_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnamaangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaangActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
           nopem=txtnopin.getText();
           int tny = JOptionPane.showConfirmDialog(null, "Yakin Ingin Hapus Data ?","Tanya",JOptionPane.YES_NO_OPTION);
        if (tny==0) {
        try {
            sql=" delete from tb_peminjaman where no_peminjaman='"+ nopem +"'";
            st=con.createStatement();
            st.execute(sql);
            tampilPeminjaman("select * from tb_peminjaman");
            JOptionPane.showMessageDialog(null,"Data Sudah Di Hapus ");
            btnsimpan.hide();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Tidak ada data yang di Hapus ....!! \n"
                +e.getMessage());
        }
        }
        kosongkan();
                       
    }//GEN-LAST:event_btnhapusActionPerformed

    
    private void cmbkodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbkodeItemStateChanged
        // TODO add your handling code here:
         try {
            sql="select * from tb_buku where kode_buku='"+ cmbkode.getSelectedItem()+"'";
            st=con.createStatement();
            rsbuku=st.executeQuery(sql);
            while (rsbuku.next()) {
                txtjudul.setText(rsbuku.getString(2));
                txtpenulis.setText(rsbuku.getString(3));
                txtpenerbit.setText(rsbuku.getString(4));
                } 
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cmbkodeItemStateChanged

    private void cmbiduserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbiduserItemStateChanged
        // TODO add your handling code here:
         try {
            sql="select * from tb_user where id_user='"+ cmbiduser.getSelectedItem()+"'";
            st=con.createStatement();
            rsuser=st.executeQuery(sql);
            while (rsuser.next()) {
                txtnamaus.setText(rsuser.getString(2));
                txthakakses.setText(rsuser.getString(4));
                }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cmbiduserItemStateChanged

    private void cmbidanggotaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbidanggotaItemStateChanged
        // TODO add your handling code here:
           try {
            sql="select * from tb_anggota where id_anggota ='"+ cmbidanggota.getSelectedItem()+"'";
            st=con.createStatement();
            rsanggota=st.executeQuery(sql);
            while (rsanggota.next()) {
                txtnamaang.setText(rsanggota.getString(2));
                }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cmbidanggotaItemStateChanged

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
       nopem=txtnopin.getText();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tanggal =format.format(DCtanggal.getDate());
        idus=cmbiduser.getItemAt(cmbiduser.getSelectedIndex()).toString();
        idang=cmbidanggota.getItemAt(cmbidanggota.getSelectedIndex()).toString();
        totpin=Integer.parseInt (txttot_pinjam.getText());
       simpandetail_peminjaman();
        try {
            sql=" insert into tb_peminjaman "
            + "(no_peminjaman,tanggal_pinjam,id_user,"
            + "id_anggota,total_pinjam)"
            + "values('"+ nopem +"','"+ tanggal +"',"
            + "'"+ idus +"','"+ idang +"','"+ totpin +"')";
            st=con.createStatement();
            st.execute(sql);
            tampilPeminjaman("select * from tb_peminjaman");
            JOptionPane.showMessageDialog(null,"Data Sudah Di Simpan");
            kosongkan();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Data Yang Di Input Salah ....!! \n"
                +e.getMessage());
        }                                        
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void DCtanggalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DCtanggalPropertyChange
        // TODO add your handling code here:
       if (DCtanggal.getDate()!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            tanggal =format.format(DCtanggal.getDate());
        }
    }//GEN-LAST:event_DCtanggalPropertyChange

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        // TODO add your handling code here:
        int tny = JOptionPane.showConfirmDialog(null, "Yakin Ingin Keluar ?","Tanya",JOptionPane.YES_NO_OPTION);
        if (tny==0) {     
       try {
            System.exit(0);
        } catch (Exception e) {
        }
        }
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void txtnopinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnopinKeyPressed
        // TODO add your handling code here:
        nopem=txtnopin.getText();
        int ascii = evt.getKeyCode();
        if(ascii==10){
            try {
                sql="select * from tb_peminjaman where no_peminjaman='"+ nopem +"'";
                st=con.createStatement();
                rspinjam=st.executeQuery(sql);
                while (rspinjam.next()){
                    DCtanggal.setDateFormatString(rspinjam.getString(2));
                    cmbiduser.setSelectedItem(rspinjam.getString(3));
                    cmbidanggota.setSelectedItem(rspinjam.getString(4));
                    txttot_pinjam.setText(rspinjam.getString(5));
                    btnhapus.enable(true);
                }
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Data Tidak Di Temukan ....!! \n"
                    +e.getMessage());
                DCtanggal.requestFocus();
            } 
        }
    }//GEN-LAST:event_txtnopinKeyPressed

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
        // TODO add your handling code here:
      kosongkan();
    }//GEN-LAST:event_btnbatalActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    
    }//GEN-LAST:event_formWindowActivated

    private void btntambahbukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahbukuActionPerformed
        // TODO add your handling code here:
        prosestambah();
        total_pinjam();
    }//GEN-LAST:event_btntambahbukuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new peminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambahbuku;
    private javax.swing.JComboBox<String> cmbidanggota;
    private javax.swing.JComboBox<String> cmbiduser;
    private javax.swing.JComboBox<String> cmbkode;
    private javax.swing.JTable detail_peminjaman;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_peminjaman;
    private javax.swing.JTextField txthakakses;
    private javax.swing.JTextField txtjml_pinjam;
    private javax.swing.JTextField txtjudul;
    private javax.swing.JTextField txtnamaang;
    private javax.swing.JTextField txtnamaus;
    private javax.swing.JTextField txtnopin;
    private javax.swing.JTextField txtpenerbit;
    private javax.swing.JTextField txtpenulis;
    private javax.swing.JTextField txttot_pinjam;
    // End of variables declaration//GEN-END:variables
}
