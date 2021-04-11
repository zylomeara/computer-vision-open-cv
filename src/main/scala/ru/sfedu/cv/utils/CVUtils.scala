package ru.sfedu.cv.utils

import java.awt.FlowLayout
import java.awt.image.{BufferedImage, DataBufferByte}

import javax.swing.{ImageIcon, JFrame, JLabel}
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import ru.sfedu.cv.enums.ChannelsEnum

object CVUtils extends Logger {
  def showImage(srcImage: Mat, title: String = ""): Unit = {
    val m = srcImage
    var `type` = BufferedImage.TYPE_BYTE_GRAY
    if (m.channels == ChannelsEnum.COLOR.id) `type` = BufferedImage.TYPE_3BYTE_BGR
    val bufferSize = m.channels * m.cols * m.rows
    val b = new Array[Byte](bufferSize)
    m.get(0, 0, b)
    val image = new BufferedImage(m.cols, m.rows, `type`)
    val targetPixels = image.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData
    System.arraycopy(b, 0, targetPixels, 0, b.length)
    val icon = new ImageIcon(image)
    val frame = new JFrame
    frame.setLayout(new FlowLayout)
    frame.setSize(image.getWidth(null) + 50, image.getHeight(null) + 50)
    val lbl = new JLabel
    lbl.setIcon(icon)
    frame.add(lbl)
    frame.setVisible(true)
    frame.setTitle(title)
    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    log.info(s"Image shown ${if (!title.isEmpty) s"with title: $title" else ""}")
  }

  def saveImage(srcImage: Mat, imagePath: String): Unit = {
    Imgcodecs.imwrite(imagePath, srcImage)
    log.info("Image saved: " + imagePath)
  }
}
