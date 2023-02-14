  cd inggame
  #sudo apt install -y jq
  function plugin() {
      latest=$(curl -sL https://api.github.com/repos/$1/releases/latest | jq -r ".tag_name")
      cd plugins
      wget -f -O $2.jar https://github.com/$1/releases/download/$latest/$2-$latest.jar
      cd ..
  }

  plugin IntellectualSites/FastAsyncWorldEdit FastAsyncWorldEdit-Bukkit
  plugin ViaVersion/ViaVersion ViaVersion
  plugin ViaVersion/ViaBackwards ViaBackwards

# Mirai
#  wget -O server.jar https://ci.codemc.io/job/etil2jz/job/Mirai-1.19/lastSuccessfulBuild/artifact/build/libs/mirai-paperclip-1.19.2-R0.1-SNAPSHOT-reobf.jar

# Purpur
  VERSION=$(<version.txt)
  wget -O server.jar https://api.purpurmc.org/v2/purpur/$VERSION/latest/download

# pufferfish
# wget -O server.jar https://ci.pufferfish.host/job/Pufferfish-1.19/lastBuild/artifact/build/libs/pufferfish-paperclip-1.19.3-R0.1-SNAPSHOT-reobf.jar
#java -Xms12G -Xmx12G -jar server.jar
cd customized_game_world
rm -fvr !"paper-world.yml"
cd ..
java -Xms2G -Xmx2G -Djava.compiler=jitc -XX:ReservedCodeCacheSize=2G -XX:InitialCodeCacheSize=2G -XX:CompileThreshold=1 -XX:TieredStopAtLevel=1 -jar --add-modules jdk.incubator.vector server.jar
