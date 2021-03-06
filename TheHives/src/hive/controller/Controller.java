
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hive.controller;

import hive.model.Match;
import hive.model.game.Game;
import hive.model.game.PrecalculatedGame;
import hive.model.players.PlayerData;
import hive.model.players.PlayersData;
import hive.model.players.TeamColor;
import hive.model.players.decisions.Decision;
import hive.model.players.decisions.HumanDecision;
import hive.model.players.decisions.IADecision;
import hive.model.players.decisions.IA.Level;
import hive.vue.CacheImage;
import hive.vue.Interface;
import hive.vue.InterfaceCharger;
import hive.vue.InterfaceCredits;
import hive.vue.InterfaceJoueurs;
import hive.vue.InterfaceMenu;
import hive.vue.InterfacePlateau;
import hive.vue.InterfaceRegles;
import hive.vue.InterfaceStatistiques;
import hive.vue.Preferences;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Locale;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lucas
 */
public final class Controller
{

    Scene currentScene;
    Stage primaryStage;
    CacheImage cacheImage;
    public Dimension screenSize;
    public LanguagesGesture gestionnaireLangage;
    public StatistiqueGesture scoresGesture;
    public String typeTheme;
    public SettingsGesture settingsGesture;

    public Controller(Stage _primaryStage, Scene _currentScene, CacheImage _cacheImage, Dimension _screenSize)
    {

        currentScene = _currentScene;
        primaryStage = _primaryStage;
        cacheImage = _cacheImage;
        screenSize = _screenSize;
        settingsGesture = new SettingsGesture();
        gestionnaireLangage = new LanguagesGesture(settingsGesture.get("langue"));
        primaryStage.setScene(currentScene);
        goToMenu();
    }

    public void goToMenu()
    {
        currentScene.setRoot(new InterfaceMenu(currentScene, primaryStage, this, cacheImage));
    }

    public void goToChoixJoueur()
    {
        currentScene.setRoot(new InterfaceJoueurs(currentScene, primaryStage, this, cacheImage));
    }

    public void goToPlateau(PlayerData data1, PlayerData data2, Level levelJ1, Level levelJ2)
    {
        Game game = PrecalculatedGame.get(PrecalculatedGame.Id.DEFAULT, getDecision(levelJ1), getDecision(levelJ2));
        PlayersData data = new PlayersData(game.getPlayer(TeamColor.WHITE), data1, game.getPlayer(TeamColor.BLACK), data2);
        Match match = new Match(game, data);
        currentScene.setRoot(new InterfacePlateau(currentScene, primaryStage, this, match, cacheImage));
        String css = this.getClass().getResource("/hive/vue/style.css").toExternalForm();
        currentScene.getStylesheets().add(css);
    }

    public void goToPlateau(Match match)
    {
        currentScene.setRoot(new InterfacePlateau(currentScene, primaryStage, this, match, cacheImage));
        String css = this.getClass().getResource("/hive/vue/style.css").toExternalForm();
        currentScene.getStylesheets().add(css);
    }

    public void goToChargerPartie() throws IOException
    {
        currentScene.setRoot(new InterfaceCharger(currentScene, primaryStage, this, cacheImage));
    }

    public void goToRegles()
    {
        currentScene.setRoot(new InterfaceRegles(currentScene, primaryStage, this, cacheImage));
    }

    public void goToStat()
    {
        currentScene.setRoot(new InterfaceStatistiques(currentScene, primaryStage, this, cacheImage));
    }

    public void goToCredits()
    {
        currentScene.setRoot(new InterfaceCredits(currentScene, primaryStage, this, cacheImage));
    }

    private Decision getDecision(Level level)
    {
        return level == null ? new HumanDecision() : new IADecision(level);
    }

    public Preferences getPreferences()
    {
        return new Preferences(currentScene, this, cacheImage);
    }

    public void validerParametres(String nomLangue, boolean activerAide, String nomTheme)
    {
        Locale newLangue = gestionnaireLangage.languages.get(nomLangue);
        gestionnaireLangage.setLanguage(newLangue);
        settingsGesture.set("langue", nomLangue);
        settingsGesture.set("aide", activerAide ? "true" : "false");
        settingsGesture.set("theme", nomTheme);
        ((Interface) currentScene.getRoot()).majRetourPreference();
    }
}
