class Prowl {

  static add(apikey, application, event, description, priority = 0) {
    use(StringCategory) {
      send "https://prowl.weks.net/publicapi/add?apikey=${apikey}&application=${application.encode()}&event=${event.encode()}&description=${description.encode()}&priority=${priority}"
    }
  }

  static verify(apikey) {
    send "https://prowl.weks.net/publicapi/verify?apikey=${apikey}"
  }

  static send(url) {
    def result = new Expando()
    try {
      def prowl = new XmlParser().parseText(url.toURL().text)
      result.remaining = prowl.success[0].@remaining
      result.resetdate = new Date(Long.valueOf(prowl.success[0].@resetdate) * 1000)
      result.success = true
    } catch (Exception ex) {
      result.success = false
      result.errorMessage = ex.message
    }
    return result
  }

}

class StringCategory {
  static encode(string) {
    URLEncoder.encode(string, "UTF-8")
  }
}
