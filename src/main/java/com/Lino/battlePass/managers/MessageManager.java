package com.Lino.battlePass.managers;

import com.Lino.battlePass.BattlePass;
import com.Lino.battlePass.utils.GradientColorParser;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private final BattlePass plugin;
    private FileConfiguration messagesConfig;

    public MessageManager(BattlePass plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        messagesConfig = plugin.getConfigManager().getMessagesConfig();
    }

    public String getMessage(String path, Object... replacements) {
        String message = messagesConfig.getString(path, path);

        if (replacements.length > 0 && replacements.length % 2 == 0) {
            for (int i = 0; i < replacements.length; i += 2) {
                String target = replacements[i] != null ? replacements[i].toString() : "null";
                String replacement = replacements[i + 1] != null ? replacements[i + 1].toString() : "null";
                message = message.replace(target, replacement);
            }
        }

        return GradientColorParser.parse(message);
    }

    public List<String> getMessages(String path, Object... replacements) {
        List<String> messages = messagesConfig.getStringList(path);
        List<String> processedMessages = new ArrayList<>();

        for (String message : messages) {
            String processedLine = message;
            if (replacements.length > 0 && replacements.length % 2 == 0) {
                for (int i = 0; i < replacements.length; i += 2) {
                    String target = replacements[i] != null ? replacements[i].toString() : "null";
                    String replacement = replacements[i + 1] != null ? replacements[i + 1].toString() : "null";
                    processedLine = processedLine.replace(target, replacement);
                }
            }
            processedMessages.add(GradientColorParser.parse(processedLine));
        }
        return processedMessages;
    }

    public String getPrefix() {
        return GradientColorParser.parse(
                messagesConfig.getString("prefix", "&6&l[BATTLE PASS] &e"));
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }
}
