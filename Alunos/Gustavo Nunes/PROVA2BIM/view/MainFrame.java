package view;

import controller.SeriesController;
import model.entities.Serie;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class MainFrame extends JFrame {

    /// Attributes
    private final SeriesController controller;
    private JTextField searchField;
    private JButton searchButton;
    private JButton favoriteButton;
    private JButton watchedButton;
    private JButton wantToWatchButton;
    private DefaultListModel<Serie> resultsModel;
    private JList<Serie> resultsList;
    private JScrollPane resultsScrollPane;
    private JPanel searchPanel;
    private JPanel actionsPanel;
    private JTabbedPane tabbedPane;
    private JButton removeButton;
    private DefaultListModel<Serie> favoritesModel;
    private DefaultListModel<Serie> watchedModel;
    private DefaultListModel<Serie> wantToWatchModel;
    private JList<Serie> favoritesList;
    private JList<Serie> watchedList;
    private JList<Serie> wantToWatchList;
    private JLabel userLabel;
    private JLabel favoritesCountLabel;
    private JLabel watchedCountLabel;
    private JLabel wantToWatchCountLabel;
    private JPanel statisticsPanel;
    private JComboBox<String> sortComboBox;
    private JMenuItem changeNicknameItem;
    private JMenuItem aboutItem;

    /// Constructor
    public MainFrame() throws IOException {

        this.controller = new SeriesController();

        this.checkUser();

        this.initializeComponents();

        this.addMenuBar();

        this.addTopPanel();

        this.addStatisticsPanel();

        this.addResultList();

        this.addActionButtons();

        this.addTabs();

        this.refreshLists();

        this.assembleLayout();

        this.configureEvents();

        setVisible(true);
    }

    /// Checa se usuário existe, caso não, cria um
    private void checkUser() throws IOException {

        if (this.controller.getUserData().getUser() == null) {

            String nickname =
                    NicknameDialog.askNickname();

            if (nickname != null &&
                    !nickname.isBlank()) {

                this.controller.setNickname(
                        nickname.trim()
                );
            }
        }
    }

    /// Configura a o frame
    private void initializeComponents() {

        String title = "TV Series APP";

        setTitle(title);

        setSize(1000, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /// Adiciona a área para pesquisa e e caixa de ordenação
    private void addTopPanel() {

        this.searchPanel = new JPanel();

        this.searchField = new JTextField(30);

        this.searchButton = new JButton("Buscar");

        this.searchPanel.add(this.searchField);
        this.searchPanel.add(this.searchButton);

        this.sortComboBox = new JComboBox<>(new String[]{
                "Nome",
                "Nota",
                "Status",
                "Estreia"
        });

        this.searchPanel.add(this.sortComboBox);

    }

    /// Adiciona a lista de resultados da pesquisa
    private void addResultList() {

        this.resultsModel = new DefaultListModel<>();

        this.resultsList = new JList<>(this.resultsModel);

        this.resultsScrollPane =
                new JScrollPane(this.resultsList);

    }
    /// Adiciona os botões para adicionar e remover series do usuário
    private void addActionButtons() {

        this.favoriteButton = new JButton("Favorito");
        this.watchedButton = new JButton("Assistido");
        this.wantToWatchButton = new JButton("Quero Assistir");
        this.removeButton = new JButton("Remover");

        this.actionsPanel = new JPanel();

        this.actionsPanel.add(this.favoriteButton);
        this.actionsPanel.add(this.watchedButton);
        this.actionsPanel.add(this.wantToWatchButton);
        this.actionsPanel.add(this.removeButton);
    }

    /// Adicona abas para as séries do usuário
    private void addTabs() {

        this.tabbedPane = new JTabbedPane();

        this.favoritesModel =
                new DefaultListModel<>();

        this.favoritesList =
                new JList<>(favoritesModel);

        this.tabbedPane.addTab(
                "Favoritos",
                new JScrollPane(favoritesList)
        );

        this.watchedModel =
                new DefaultListModel<>();

        this.watchedList =
                new JList<>(watchedModel);

        this.tabbedPane.addTab(
                "Assistidos",
                new JScrollPane(watchedList)
        );

        this.wantToWatchModel =
                new DefaultListModel<>();

        this.wantToWatchList =
                new JList<>(wantToWatchModel);

        this.tabbedPane.addTab(
                "Quero Assistir",
                new JScrollPane(wantToWatchList)
        );
    }

    /// Exibe detlhes da serie quando está sofrer duplo click
    private void addDetailEvent(JList<Serie> list) {

        list.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        if (e.getClickCount() == 2) {

                            Serie selected = list.getSelectedValue();

                            if (selected != null) {

                                new SerieDetailsDialog(
                                        MainFrame.this,
                                        selected
                                ).setVisible(true);
                            }
                        }
                    }
                }
        );
    }

    /// Adiciona os elementos que compoem o layout
    private void assembleLayout() {

        setLayout(new BoxLayout(
                getContentPane(),
                BoxLayout.Y_AXIS
        ));

        add(this.searchPanel);
        add(this.statisticsPanel);
        add(this.resultsScrollPane);
        add(this.actionsPanel);
        add(this.tabbedPane);
    }

    /// Configura todas as ações que poden ser feitas durante a esecução
    private void configureEvents() {

        this.configureSearchEvent();

        this.configureFavoriteEvent();

        this.configureWatchedEvent();

        this.configureWantToWatchEvent();

        this.configureRemoveEvent();

        this.configureSeriesDetailsEvent();

        this.configureSortEvent();

        this.configureNickname();

        this.configureAboutEvent();
    }

    /// Pesquisa serie
    private void configureSearchEvent() {

        this.searchButton.addActionListener(e -> {

            try {

                searchSeries();

            } catch (Exception exception) {

                JOptionPane.showMessageDialog(
                        this,
                        exception.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    /// Adiciona série aos favoritos
    private void configureFavoriteEvent() {

        this.favoriteButton.addActionListener(e -> {

            Serie selected =
                    this.resultsList.getSelectedValue();

            if (selected == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma série."
                );

                return;
            }

            try {

                this.controller.addFavorite(selected);

                this.refreshFavorites();

                JOptionPane.showMessageDialog(
                        this,
                        "Série adicionada aos favoritos."
                );

            } catch (Exception exception) {

                JOptionPane.showMessageDialog(
                        this,
                        exception.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    /// Adiciona série aos assistidos
    private void configureWatchedEvent() {

        this.watchedButton.addActionListener(e -> {

            Serie selected =
                    this.resultsList.getSelectedValue();

            if (selected == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma série."
                );

                return;
            }

            try {

                this.controller.addWatched(selected);

                this.refreshWatched();

                JOptionPane.showMessageDialog(
                        this,
                        "Série adicionada aos assistidos."
                );

            } catch (Exception exception) {

                JOptionPane.showMessageDialog(
                        this,
                        exception.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    /// Adiciona série aos quero assistir
    private void configureWantToWatchEvent() {

        this.wantToWatchButton.addActionListener(e -> {

            Serie selected =
                    this.resultsList.getSelectedValue();

            if (selected == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma série."
                );

                return;
            }

            try {

                this.controller.addWantToWatch(selected);

                this.refreshWantToWatch();

                JOptionPane.showMessageDialog(
                        this,
                        "Série adicionada à lista."
                );

            } catch (Exception exception) {

                JOptionPane.showMessageDialog(
                        this,
                        exception.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        });

    }

    /// Remove série da coleção onde ela estiver
    private void configureRemoveEvent() {

        this.removeButton.addActionListener(e -> {

            try {

                removeSelectedSerie();

            } catch (Exception exception) {

                JOptionPane.showMessageDialog(
                        this,
                        exception.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    /// Menu para alteração do apelido
    private void configureNickname() {

        this.changeNicknameItem
                .addActionListener(
                        e -> {
                            try {

                                changeNickname();

                            } catch (Exception exception) {

                                JOptionPane.showMessageDialog(
                                        this,
                                        exception.getMessage(),
                                        "Erro",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                );
    }

    /// Informações sobre o projeto
    private void configureAboutEvent() {

        this.aboutItem.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    """
                            
                            TV Series APP
                            
                            Projeto acadêmico em Java Swing.
                            Dados obtidos pela API TVMaze.
                            
                            """
            );
        });
    }

    /// Acessa o backand para pesquisar série
    private void searchSeries()
            throws IOException, InterruptedException {

        String name =
                this.searchField.getText().trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite o nome de uma série."
            );

            return;
        }

        List<Serie> series =
                this.controller.searchSeries(name);

        this.resultsModel.clear();

        for (Serie serie : series) {

            this.resultsModel.addElement(serie);
        }
    }

    /// Atualiza todos os dados exibidos
    private void refreshLists() {

        this.refreshFavorites();

        this.refreshWatched();

        this.refreshWantToWatch();

        this.refreshUserStatistics();

    }

    /// Atualiza a exibição de favoritos
    private void refreshFavorites() {

        favoritesModel.clear();

        for (Serie serie :
                controller.getFavorite()) {

            favoritesModel.addElement(serie);
        }

        this.refreshFavoritesStatistics();

    }

    /// Atualiza a exibição de assistidos
    private void refreshWatched() {

        watchedModel.clear();

        for (Serie serie :
                controller.getWatched()) {

            watchedModel.addElement(serie);
        }

        this.refreshWatchedStatistics();
    }

    /// Atualiza a exibição de quero assistir
    private void refreshWantToWatch() {

        wantToWatchModel.clear();

        for (Serie serie :
                controller.getWantToWatch()) {

            wantToWatchModel.addElement(serie);
        }

        this.refreshWantToWatchStatistics();
    }

    /// Remove uma seria de uma coleção especifica, acessa o backand
    private void removeSelectedSerie()
            throws IOException {

        int selectedTab =
                this.tabbedPane.getSelectedIndex();

        Serie selected = null;

        switch (selectedTab) {

            case 0:

                selected =
                        this.favoritesList.getSelectedValue();

                if (selected != null) {

                    controller.removeFavorite(selected);

                    refreshFavorites();
                }

                break;

            case 1:

                selected =
                        this.watchedList.getSelectedValue();

                if (selected != null) {

                    controller.removeWatched(selected);

                    refreshWatched();
                }

                break;

            case 2:

                selected =
                        this.wantToWatchList.getSelectedValue();

                if (selected != null) {

                    controller.removeWantToWatch(selected);

                    refreshWantToWatch();
                }

                break;
        }

        if (selected == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma série para remover."
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Série removida."
        );
    }

    /// Configura o evento de detalhes da serie
    private void configureSeriesDetailsEvent() {

        this.addDetailEvent(resultsList);
        this.addDetailEvent(favoritesList);
        this.addDetailEvent(watchedList);
        this.addDetailEvent(wantToWatchList);

    }

    /// Adiciona as estatísticas do usuário
    private void addStatisticsPanel() {

        this.statisticsPanel = new JPanel();

        this.userLabel = new JLabel();

        this.favoritesCountLabel = new JLabel();

        this.watchedCountLabel = new JLabel();

        this.wantToWatchCountLabel = new JLabel();

        this.statisticsPanel.add(userLabel);

        this.statisticsPanel.add(
                new JLabel(" | ")
        );

        this.statisticsPanel.add(
                favoritesCountLabel
        );

        this.statisticsPanel.add(
                new JLabel(" | ")
        );

        this.statisticsPanel.add(
                watchedCountLabel
        );

        this.statisticsPanel.add(
                new JLabel(" | ")
        );

        this.statisticsPanel.add(
                wantToWatchCountLabel
        );
    }

    /// Atualiza exibição do usuário
    private void refreshUserStatistics() {

        if (controller.getUserData().getUser() == null) {
            userLabel.setText("Usuário: Anônimo");
            return;
        }

        userLabel.setText(
                "Usuário: "
                        + controller.getUserData()
                        .getUser()
                        .getNickName()
        );
    }

    /// Atualiza a contagen de favoritos
    private void refreshFavoritesStatistics() {

        favoritesCountLabel.setText(
                "Favoritos: "
                        + controller.getFavorite().size()
        );
    }

    /// Atualiza a contagen de assistidos
    private void refreshWatchedStatistics() {

        watchedCountLabel.setText(
                "Assistidos: "
                        + controller.getWatched().size()
        );
    }

    /// Atualiza a contagen de quero assistir
    private void refreshWantToWatchStatistics() {

        wantToWatchCountLabel.setText(
                "Quero Assistir: "
                        + controller.getWantToWatch().size()
        );
    }

    /// Configura evento de ordenação
    private void configureSortEvent() {

        sortComboBox.addActionListener(
                e -> sortCurrentTab()
        );

    }

    /// Ordena a aba em exibição no momento
    private void sortCurrentTab() {

        int tab = tabbedPane.getSelectedIndex();

        String option =
                (String) sortComboBox.getSelectedItem();

        List<Serie> series;
        List<Serie> sorted;

        switch (tab) {

            case 0:
                series = controller.getFavorite();
                break;

            case 1:
                series = controller.getWatched();
                break;

            case 2:
                series = controller.getWantToWatch();
                break;

            default:
                return;
        }

        switch (option) {

            case "Nome":
                sorted = controller.sortByName(series);
                break;

            case "Nota":
                sorted = controller.sortByRating(series);
                break;

            case "Status":
                sorted = controller.sortByStatus(series);
                break;

            case "Estreia":
                sorted = controller.sortByPremiered(series);
                break;

            default:
                return;
        }

        updateCurrentTab(sorted);

    }

    /// Atualiza a exibição da uma aba
    private void updateCurrentTab(List<Serie> series) {

        int tab = tabbedPane.getSelectedIndex();

        DefaultListModel<Serie> model;

        switch (tab) {

            case 0:
                model = favoritesModel;
                break;

            case 1:
                model = watchedModel;
                break;

            case 2:
                model = wantToWatchModel;
                break;

            default:
                return;
        }

        model.clear();

        for (Serie serie : series) {
            model.addElement(serie);
        }
    }

    /// Instancia os menus de usuário e informações
    private void addMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu userMenu = new JMenu("Usuário");

        this.changeNicknameItem =
                new JMenuItem("Alterar Nickname");

        userMenu.add(changeNicknameItem);

        JMenu helpMenu = new JMenu("Ajuda");

        this.aboutItem =
                new JMenuItem("Sobre");

        helpMenu.add(aboutItem);

        menuBar.add(userMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /// Troca o apelido do usuário
    private void changeNickname()
            throws IOException {

        String nickname =
                JOptionPane.showInputDialog(
                        this,
                        "Novo nickname: "
                );

        if (nickname == null ||
                nickname.isBlank()) {

            return;
        }

        this.controller.setNickname(
                nickname.trim()
        );

        refreshUserStatistics();

    }

}
