GIT_URL=https://Bruce0203:$GH_TOKEN@github.com/IngGameTeam/inggame

#BASEDIR=$(dirname $0)
#cd $BASEDIR
  if [ -d ".git" ]; then
      echo "pull!"
      cd plugins
      git stash
      git stash drop
      git pull origin bukkit
      cd ..
  else
      echo "clone!"
      cd ..
      git remote set-url origin $GIT_URL
      git clone -b bukkit --single-branch $GIT_URL
      cd inggame
  fi
