/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.6.14 : Database - db_perpustakaan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_perpustakaan` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `db_perpustakaan`;

/*Table structure for table `detail_pinjam` */

DROP TABLE IF EXISTS `detail_pinjam`;

CREATE TABLE `detail_pinjam` (
  `no_peminjaman` varchar(5) DEFAULT NULL,
  `kode_buku` varchar(7) DEFAULT NULL,
  `jumlah_pinjam` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `detail_pinjam` */

insert  into `detail_pinjam`(`no_peminjaman`,`kode_buku`,`jumlah_pinjam`) values ('001','KDB-E01',1),('001','KDB-E02',1),('001','KDB-K01',1),('001','KDB-E01',1),('001','KDB-E02',1),('001','KDB-K01',1),('0011','KDB-E01',1),('0011','KDB-E02',1),('0011','KDB-K01',1),('0011','KDB-E01',1),('0011','KDB-E02',1),('0011','KDB-K01',1),('0011','KDB-E01',1),('0011','KDB-E02',1),('0011','KDB-K01',1),('0011','KDB-E01',1),('0011','KDB-E02',1),('0011','KDB-K01',1),('00011','KDB-E01',1),('00011','KDB-E02',1),('00011','KDB-K01',1),('001','KDB-E01',1),('001','KDB-E01',1),('001','KDB-E01',1),('001','KDB-E01',1),('001','KDB-E02',1),('001','KDB-K01',1),('001','KDB-E02',1),('001','KDB-E02',1),('001','KDB-K01',1),('1234','KDB-E01',1),('1234','KDB-E02',1),('1234','KDB-K01',1),('NP001','KDB-A01',1),('NP001','KDB-M01',1),('NP001','KDB-M02',1),('NP002','KDB-A01',1),('NP002','KDB-P01',1),('NP002','KDB-S01',1),('NP003','KDB-M01',1),('NP003','KDB-M04',1),('NP003','KDB-S01',1),('np02','KDB-A01',1);

/*Table structure for table `tb_anggota` */

DROP TABLE IF EXISTS `tb_anggota`;

CREATE TABLE `tb_anggota` (
  `id_anggota` varchar(7) NOT NULL,
  `nama_anggota` varchar(25) DEFAULT NULL,
  `jenis_kelamin` varchar(9) DEFAULT NULL,
  `alamat` varchar(35) DEFAULT NULL,
  `no_hp` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id_anggota`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tb_anggota` */

insert  into `tb_anggota`(`id_anggota`,`nama_anggota`,`jenis_kelamin`,`alamat`,`no_hp`) values ('IDA001','Yunita Sari','Perempuan','Jl. Sudirman','08123697389'),('IDA002','Adi Saputra','Laki-Laki','Jl. Sukajadi','08124475858'),('IDA003','Ayu Sari','Perempuan','Jl. Pemuda','081252747484'),('IDA004','Julia','Perempuan','Jl. Mulia','08124757584');

/*Table structure for table `tb_buku` */

DROP TABLE IF EXISTS `tb_buku`;

CREATE TABLE `tb_buku` (
  `kode_buku` varchar(7) NOT NULL,
  `judul_buku` varchar(35) DEFAULT NULL,
  `penulis` varchar(30) DEFAULT NULL,
  `penerbit` varchar(35) DEFAULT NULL,
  `tahun_terbit` varchar(4) DEFAULT NULL,
  `jumlah_buku` int(3) DEFAULT NULL,
  PRIMARY KEY (`kode_buku`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tb_buku` */

insert  into `tb_buku`(`kode_buku`,`judul_buku`,`penulis`,`penerbit`,`tahun_terbit`,`jumlah_buku`) values ('KDB-A01','Pendidikan Agama Islam','Mustahdi','Politeknik Negeri','2014',7),('KDB-M01','Matematika','Kurniawan','Erlangga','2013',6),('KDB-M02','Matematika','Ibnu Taufiq','Pusat Kurikulum dan Perbukuan','2013',7),('KDB-M03','Matematika','Permata Sari','Erlangga','2012',5),('KDB-M04','Matematika','Purwanto','Erlangga','2010',6),('KDB-P01','Prakarya','Erny Yuliani','Pusat Kurikulum dan Perbukuan','2013',6),('KDB-S01','Seni Budaya','Muhammad Nuh','Pusat Kurikulum dan Perbukuan','2013',7);

/*Table structure for table `tb_peminjaman` */

DROP TABLE IF EXISTS `tb_peminjaman`;

CREATE TABLE `tb_peminjaman` (
  `no_peminjaman` varchar(5) NOT NULL,
  `tanggal_pinjam` date DEFAULT NULL,
  `id_user` varchar(7) DEFAULT NULL,
  `id_anggota` varchar(7) DEFAULT NULL,
  `total_pinjam` int(1) DEFAULT NULL,
  PRIMARY KEY (`no_peminjaman`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tb_peminjaman` */

insert  into `tb_peminjaman`(`no_peminjaman`,`tanggal_pinjam`,`id_user`,`id_anggota`,`total_pinjam`) values ('NP001','2016-11-26','IDP102','IDA001',3),('NP002','2016-11-26','IDP101','IDA001',3),('np02','2016-11-28','IDP101','IDA001',1);

/*Table structure for table `tb_pengembalian` */

DROP TABLE IF EXISTS `tb_pengembalian`;

CREATE TABLE `tb_pengembalian` (
  `no_pengembalian` varchar(5) NOT NULL,
  `tanggal_kembali` date DEFAULT NULL,
  `no_peminjaman` varchar(5) DEFAULT NULL,
  `keterlambatan` int(3) DEFAULT NULL,
  `denda` int(5) DEFAULT NULL,
  PRIMARY KEY (`no_pengembalian`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tb_pengembalian` */

insert  into `tb_pengembalian`(`no_pengembalian`,`tanggal_kembali`,`no_peminjaman`,`keterlambatan`,`denda`) values ('NPB01','2016-00-26','NP001',0,0),('PK01','2016-00-25','001',0,0),('PK02','2016-00-25','001',0,0);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id_user` varchar(7) NOT NULL,
  `nama_user` varchar(25) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  `hak_akses` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id_user`,`nama_user`,`password`,`hak_akses`) values ('IDP100','Via Wati','Via16','Ketua'),('IDP101','Anisa Nissa','Nisa21','Penjaga Perpus'),('IDP102','Ayu Putri','Putri102','Penjaga Perpus'),('IDP103','Lia Pemata Sari','Lia98','Penjaga Perpus');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
