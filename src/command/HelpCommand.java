package command;

import dbg.VMHandler;

import java.util.Arrays;

public class HelpCommand implements InputCommand {

    VMHandler vmHandler;

    public HelpCommand(VMHandler vmHandler) {
        this.vmHandler = vmHandler;
    }

    @Override
    public void execute() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- step");
        stringBuilder.append("\n");
        stringBuilder.append("execute la prochaine instruction. S’il s’agit d’un appel de méthode, l’exécution entre dans cette dernière.");
        stringBuilder.append("\n");

        stringBuilder.append("- step-over");
        stringBuilder.append("\n");
        stringBuilder.append("execute la ligne courante.");
        stringBuilder.append("\n");

        stringBuilder.append("- continue");
        stringBuilder.append("\n");
        stringBuilder.append("continue l’exécution jusqu’au prochain point d’arrêt. La granularité est l’instruction step.");
        stringBuilder.append("\n");

        stringBuilder.append("- frame");
        stringBuilder.append("\n");
        stringBuilder.append("envoie et imprime la frame courante.");
        stringBuilder.append("\n");

        stringBuilder.append("- temporaries");
        stringBuilder.append("\n");
        stringBuilder.append("envoie et imprime la liste des variables temporaires de la frame courante, sous la forme de couples nom → valeur.");
        stringBuilder.append("\n");

        stringBuilder.append("- stack");
        stringBuilder.append("\n");
        stringBuilder.append("renvoie la pile d’appel de méthodes qui a amené l’exécution au point courant.");
        stringBuilder.append("\n");

        stringBuilder.append("- receiver");
        stringBuilder.append("\n");
        stringBuilder.append("envoie le receveur de la méthode courante (this).");
        stringBuilder.append("\n");

        stringBuilder.append("- sender");
        stringBuilder.append("\n");
        stringBuilder.append("envoie l’objet qui a appelé la méthode courante.");
        stringBuilder.append("\n");

        stringBuilder.append("- receiver-variables");
        stringBuilder.append("\n");
        stringBuilder.append("renvoie et imprime la liste des variables d’instance du receveur courant, sous la forme d’un couple nom → valeur.");
        stringBuilder.append("\n");

        stringBuilder.append("- method");
        stringBuilder.append("\n");
        stringBuilder.append("envoie et imprime la méthode en cours d’exécution.");
        stringBuilder.append("\n");

        stringBuilder.append("- arguments");
        stringBuilder.append("\n");
        stringBuilder.append("renvoie et imprime la liste des arguments de la méthode en cours d’exécution, sous la forme d’un couple nom → valeur.");
        stringBuilder.append("\n");

        stringBuilder.append("- print-var");
        stringBuilder.append("\n");
        stringBuilder.append("imprime la valeur de la variable passée en paramètre (le nom de la variable sera demandé après saisir la commande).");
        stringBuilder.append("\n");

        stringBuilder.append("- break");
        stringBuilder.append("\n");
        stringBuilder.append("installe un point d’arrêt à la ligne lineNumber du fichier fileName (les paramètres seront demandés après saisir la commande).");
        stringBuilder.append("\n");

        stringBuilder.append("- breakpoints");
        stringBuilder.append("\n");
        stringBuilder.append("liste les points d’arrêts actifs et leurs location dans le code.");
        stringBuilder.append("\n");

        stringBuilder.append("- break-once");
        stringBuilder.append("\n");
        stringBuilder.append("installe un point d’arrêt à la ligne lineNumber du fichier fileName. Ce point d’arrêt se désinstalle après avoir été atteint (les paramètres seront demandés après saisir la commande).");
        stringBuilder.append("\n");

        stringBuilder.append("- break-on-count");
        stringBuilder.append("\n");
        stringBuilder.append("installe un point d’arrêt à la ligne lineNumber du fichier fileName. Ce point d’arrêt ne s’active qu’après avoir été atteint un certain nombre de fois count (les paramètres seront demandés après saisir la commande).");
        stringBuilder.append("\n");

        stringBuilder.append("- break-before-method-cal");
        stringBuilder.append("\n");
        stringBuilder.append("configure l’exécution pour s’arrêter au tout début de l’exécution de la méthode methodName. (le nom sera demandé après saisir la commande).");
        stringBuilder.append("\n");

        System.out.println(stringBuilder);
    }

    @Override
    public boolean isResumable() {
        return false;
    }
}
