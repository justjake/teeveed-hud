#!/usr/bin/env jruby
# Console in context of current project
# relies on target/classes directory for Java .class files
# put in root of your Java project!

require 'pathname'
require 'pry-remote'

classes = Pathname.new(__FILE__).parent + 'target/classes'
$CLASSPATH << classes.realpath.to_s

jake = org.teton_landis.jake

# start JavaFX in background thread
Thread.new do
  org.teton_landis.jake.hud.Main.main([])
end

Platform = javafx.application.Platform

def instance
  org.teton_landis.jake.hud.Main.instance
end

# run a block that has access to the UI, in the app thread
# example:
#   with_ui do |ui|
#     ui.hideHud
#     ui.clearAlerts
#     ui.pushAlert(['small'], ['hello'], ['world', 'entity'])
#   end
def with_ui(&block)
  app = instance
  Platform.runLater do
    block.call(app)
  end
end

# binding.remote_pry
binding.pry
