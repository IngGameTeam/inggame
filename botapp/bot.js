const mineflayer = require('mineflayer')


var createBot = function() {
    let bot = mineflayer.createBot({
        host: 'localhost',
        username: 'test_bot',
        port: 25555,
    })
    bot.on('chat', (username, message) => {
      if (username === bot.username) return
      bot.chat(message)
    })
    bot.on('kicked', console.log)
    bot.on('error', console.log)
}


createBot("test1")
createBot("test2")
createBot("test3")