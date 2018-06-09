package org.telegram.plugins.echo;

import org.jetbrains.annotations.NotNull;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.message.TLMessage;
import org.telegram.api.update.*;
import org.telegram.api.updates.TLUpdateShortChatMessage;
import org.telegram.api.updates.TLUpdateShortMessage;
import org.telegram.api.user.TLAbsUser;
import org.telegram.bot.handlers.DefaultUpdatesHandler;
import org.telegram.bot.handlers.interfaces.IChatsHandler;
import org.telegram.bot.handlers.interfaces.IUsersHandler;
import org.telegram.bot.kernel.IKernelComm;
import org.telegram.bot.kernel.database.DatabaseManager;
import org.telegram.bot.kernel.differenceparameters.IDifferenceParametersService;
import org.telegram.bot.services.BotLogger;
import org.telegram.bot.structure.BotConfig;
import org.telegram.bot.structure.IUser;
import org.telegram.plugins.echo.handlers.MessageHandler;
import org.telegram.plugins.echo.handlers.TLMessageHandler;
import org.telegram.plugins.echo.structure.User;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 16 of October of 2016
 */
public class CustomUpdatesHandler extends DefaultUpdatesHandler {
    private static final String LOGTAG = "CHATUPDATESHANDLER";

    private final DatabaseManager databaseManager;
    private BotConfig botConfig;
    private MessageHandler messageHandler;
    private IUsersHandler usersHandler;
    private IChatsHandler chatsHandler;
    private TLMessageHandler tlMessageHandler;

    public CustomUpdatesHandler(IKernelComm kernelComm, IDifferenceParametersService differenceParametersService, DatabaseManager databaseManager) {
        super(kernelComm, differenceParametersService, databaseManager);
        this.databaseManager = databaseManager;
    }

    public void setConfig(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    public void setHandlers(MessageHandler messageHandler, IUsersHandler usersHandler, IChatsHandler chatsHandler, TLMessageHandler tlMessageHandler) {
        this.messageHandler = messageHandler;
        this.chatsHandler = chatsHandler;
        this.usersHandler = usersHandler;
        this.tlMessageHandler = tlMessageHandler;
    }

    @Override
    public void onTLUpdateShortMessageCustom(TLUpdateShortMessage update) {
        System.out.println("****onTLUpdateShortMessageCustom");
        System.out.println("****onTLUpdateShortMessageCustom  Message="+update.getMessage());
        System.out.println("****onTLUpdateShortMessageCustom userId="+update.getUserId());
        final IUser user = databaseManager.getUserById(update.getUserId());
        if (user != null) {
            BotLogger.info(LOGTAG, "Received message from: " + update.getUserId());
            messageHandler.handleMessage(user, update);
        }

    }

    @Override
    public void onTLUpdateNewMessageCustom(TLUpdateNewMessage update) {
        System.out.println("****onTLUpdateNewMessageCustom");
        System.out.println("chatId="+update.getMessage().getChatId());
        onTLAbsMessageCustom(update.getMessage());
    }

    @Override
    protected void onTLAbsMessageCustom(TLAbsMessage message) {
        System.out.println("*****onTLAbsMessageCustom");
        if (message instanceof TLMessage) {
            System.out.println("fromId:"+((TLMessage) message).getFromId()+"  toId"+message.getChatId());
            System.out.println("message text:"+((TLMessage) message).getMessage());
            if (((TLMessage) message).getFromId()==245519072){
                System.out.println("if (((TLMessage) message).getFromId()==245519072) == true");
                User iUser = new User(376651530);
                iUser.setUserHash(4280636452986968958l);
                messageHandler.handleMessage(iUser,(TLMessage) message);
            }
                final IUser user = databaseManager.getUserById(((TLMessage) message).getFromId());
                System.out.println("*****user"+user);
                if (user != null) {
                    messageHandler.handleMessage(user, (TLMessage) message);
                }
            BotLogger.debug(LOGTAG, "Received TLMessage");
            onTLMessage((TLMessage) message);
        } else {
            BotLogger.debug(LOGTAG, "Unsupported TLAbsMessage -> " + message.toString());
        }
    }

    @Override
    protected void onUsersCustom(List<TLAbsUser> users) {
        System.out.println("****onUsersCustom");
        usersHandler.onUsers(users);
    }

    @Override
    protected void onChatsCustom(List<TLAbsChat> chats) {
        System.out.println("****onChatsCustom(List<TLAbsChat> chats)");
        chatsHandler.onChats(chats);
    }

    /**
     * Handles TLMessage
     * @param message Message to handle
     */
    private void onTLMessage(@NotNull TLMessage message) {
        System.out.println("*****onTLMessage");
        if (message.hasFromId()) {
            final IUser user = databaseManager.getUserById(message.getFromId());
            if (user != null) {
                this.tlMessageHandler.onTLMessage(message);
            }
        }
    }

    @Override
    protected void onTLUpdateChannelNewMessageCustom(TLUpdateChannelNewMessage update) {
        System.out.println("!!!!!******onTLUpdateChannelNewMessageCustom");
        TLAbsMessage absMessage = update.getMessage();
        System.out.println("update.getChannelId()="+update.getChannelId());
        if (absMessage instanceof TLMessage){
            System.out.println("message"+((TLMessage) absMessage).getMessage());
            User iUser = new User(376651530);
            iUser.setUserHash(4280636452986968958l);
            messageHandler.handleMessage(iUser,(TLMessage) absMessage);
            System.out.println(((TLMessage) absMessage).getMessage());
            //onTLMessage((TLMessage)absMessage);
           // messageHandler.handleMessage(new User(245480645),(TLMessage)absMessage);
        }
    }

    @Override
    protected void onTLUpdateChannelMessageViewsCustom(TLUpdateChannelMessageViews update) {
        System.out.println("!!!!!******onTLUpdateChannelMessageViewsCustom");
    }

    @Override
    protected void onTLUpdateShortChatMessageCustom(TLUpdateShortChatMessage update) {
        System.out.println("!!!!!****onTLUpdateShortChatMessageCustom");
    }

}
