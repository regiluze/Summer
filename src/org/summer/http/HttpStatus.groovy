package org.summer.http

enum HttpStatus {
  CONTINUE_100(100, 'Continue'),
  SWITCHING_PROTOCOLS_101(101, 'Switching Protocols'),
  PROCESSING_102(102, 'Processing'),
  CHECKPOINT_103(103, 'Checkpoint'),
  REQUEST_URI_TOO_LONG(122, 'Request-URI too long'),

  OK_200(200, 'OK'),
  CREATED_201(201, 'Created'),
  ACCEPTED_302(202, 'Accepted'),

  MOVED_PERMANENTLY_301(301, 'Moved Permanently'),
  FOUND_302(302, 'Found'),

  NOT_FOUND(404, 'Not Found')

  int code
  String name

  HttpStatus(code, name) {
    this.code = code
    this.name = name
  }
}
