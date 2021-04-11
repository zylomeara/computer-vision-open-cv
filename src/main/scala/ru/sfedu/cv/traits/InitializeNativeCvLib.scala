package ru.sfedu.cv.traits

import ru.sfedu.cv.enums.OSType._
import ru.sfedu.cv.config.CVLibConfig._
import ru.sfedu.cv.helpers.TypeOS
import ru.sfedu.cv.utils.{InitializeOpenCV, Logger}

trait InitializeNativeCvLib extends Logger {
  if (!InitializeOpenCV.isLoaded) {
    log.debug("Checking OS.....")
    TypeOS.getOperatingSystemType match {
      case LINUX =>
        InitializeOpenCV.init(libPathLinux)
        log.debug("Loaded for Linux with path " + libPathLinux)

      case WINDOWS =>
        InitializeOpenCV.init(libPathWindows)
        log.debug("Loaded for Windows with path " + libPathWindows)

      case MACOS =>
        InitializeOpenCV.init(libPathMacOS)
        log.debug("Loaded for Mac OS with path " + libPathMacOS)

      case _ =>
        throw new Exception("Your OS does not support!!!")
    }
    log.info("Properties are loaded")
  }
}
