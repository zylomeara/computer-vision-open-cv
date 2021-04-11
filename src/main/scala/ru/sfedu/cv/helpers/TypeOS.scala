package ru.sfedu.cv.helpers

import java.util.Locale

import ru.sfedu.cv.enums.OSType

object TypeOS {
  def getOperatingSystemType = {
    val OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

    if (OS.contains("mac") || OS.contains("darwin")) OSType.MACOS
    else if (OS.contains("win")) OSType.WINDOWS
    else if (OS.contains("nux")) OSType.LINUX
    else OSType.OTHER
  }
}
