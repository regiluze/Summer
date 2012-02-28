package org.summer.route

import java.util.regex.Pattern

class Route {
  static final PLACEHOLDER_SEARCH_REGEXP = '\\{(\\w+)\\}'
  static final PLACEHOLDER_MATCH_REGEXP = '([^/?&#]+)'
  static final QUERY_PARAM_SEARCH_REGEXP = '([^/?&#=]+)=([^/?&#=]+)'
  Pattern pattern
  def paramNames = []

  static create(String path) {
    Route r = new Route()
    r.path = path
    r
  }

  void setPath(String path) {
    path = fixSlashes(path)
    path = "^${path}"
    path = processPlaceholders(path)
    path = addTrailingExtraParams(path)
    pattern = Pattern.compile(path)
  }

  boolean matches(String path) {
    path = fixSlashes(path)
    pattern.matcher(path).matches()
  }

  Map parseParamsFrom(String path) {
    def params = [:]
    params << parsePlaceholderParams(path)
    params << parseQueryParams(params.query)
    return params
  }

  private fixSlashes(path) {
    if (path.isEmpty() || '/' == path)
      return '/'
    if (endsWithSlash(path))
      return path[0..-2]
    path
  }

  private boolean endsWithSlash(String path) {
    '/' == path[-1]
  }

  private boolean startsWithSlash(String path) {
    '/' == path[0]
  }

  private processPlaceholders(path) {
    def placeholders = Pattern.compile(PLACEHOLDER_SEARCH_REGEXP).matcher(path)
    return placeholders.inject(path) { regexp, match ->
      paramNames << match[1]
      regexp.replaceFirst("\\{${match[1]}\\}", PLACEHOLDER_MATCH_REGEXP)
    }
  }

  private addTrailingExtraParams(path) {
    paramNames << 'query'
    paramNames << 'hash'
    path += '\\??(.*)?#?.*?$'
    return path as String
  }

  private parsePlaceholderParams(String path) {
    def params = [:]
    def paramValues = pattern.matcher(path)
    paramNames.eachWithIndex { name, index ->
      params[name] = paramValues[0][index + 1]
    }
    return params
  }

  private parseQueryParams(query) {
    def params = [:]
    def queryParams = Pattern.compile(QUERY_PARAM_SEARCH_REGEXP).matcher(query)
    queryParams.each { param ->
      def value = param[2]
      if (isArray(param))
        parseArrayParam(param, params, value) else
        params[param[1]] = value
    }
    return params
  }

  private parseArrayParam(param, params, value) {
    def name = getArrayName(param)
    if (!params.containsKey(name))
      params[name] = [:]
    def index = getArrayIndex(param)
    params[name][index] = value
  }

  private getArrayIndex(param) {
    param[1][param[1].indexOf("[") + 1..-2]
  }

  private getArrayName(param) {
    param[1][0..param[1].indexOf("[") - 1]
  }

  private isArray(param) {
    param[1].contains('[')
  }
}
