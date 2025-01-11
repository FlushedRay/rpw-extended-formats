package net.mightypork.rpw.gui.windows.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.mightypork.rpw.App;
import net.mightypork.rpw.Config;
import net.mightypork.rpw.Const;
import net.mightypork.rpw.gui.Gui;
import net.mightypork.rpw.gui.Icons;
import net.mightypork.rpw.gui.helpers.FileChooser;
import net.mightypork.rpw.gui.helpers.TextInputValidator;
import net.mightypork.rpw.gui.widgets.FileInput;
import net.mightypork.rpw.gui.widgets.SimpleStringList;
import net.mightypork.rpw.gui.widgets.VBox;
import net.mightypork.rpw.gui.windows.RpwDialog;
import net.mightypork.rpw.gui.windows.messages.Alerts;
import net.mightypork.rpw.project.Projects;
import net.mightypork.rpw.tasks.Tasks;
import net.mightypork.rpw.tasks.sequences.SequenceExportProject;
import net.mightypork.rpw.utils.files.FileUtils;
import net.mightypork.rpw.utils.files.OsUtils;
import net.mightypork.rpw.utils.files.SimpleConfig;
import net.mightypork.rpw.utils.logging.Log;

import com.google.gson.reflect.TypeToken;


public class DialogExport extends RpwDialog {

    private final List<String> installedPackNames;

    private JTextField nameField;
    private JTextField descriptionField;
    private JButton buttonOK;
    private SimpleStringList list;
    private JButton buttonCancel;
    private JCheckBox exportToMc;
    private FileInput filepicker;

    private JComboBox mcOptsCombo;
    private JComboBox packMeta;
    private JCheckBox unZip;

    private static final int MC_ALONE = 0;
    private static final int MC_ADD = 1;
    private static final int MC_NO_CHANGE = 2;


    public DialogExport() {
        super(App.getFrame(), "Export");

        installedPackNames = getOptions();

        createDialog();
    }


    @Override
    protected JComponent buildGui() {
        final VBox vbox = new VBox();
        vbox.windowPadding();

        vbox.heading("Export");

        vbox.titsep("Installed Resourcepacks");
        vbox.gap();

        vbox.add(list = new SimpleStringList(installedPackNames, true));
        list.getList().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                final String s = list.getSelectedValue();
                if (s != null) nameField.setText(s);
            }
        });

        vbox.gapl();

        vbox.titsep("Export options");
        vbox.gap();

        nameField = Gui.textField("", "Output file name", "Output file name (without extension)");
        nameField.addKeyListener(TextInputValidator.filenames());
        descriptionField = Gui.textField("", "Output file description", "Output file description");

        exportToMc = Gui.checkbox(true);

        final String[] choices = new String[3];
        choices[MC_ALONE] = "Use this pack alone";
        choices[MC_ADD] = "Add pack to selected (on top)";
        choices[MC_NO_CHANGE] = "Don't change settings";

        Config.CHOICE_EXPORT_TO_MC = Math.max(0, Math.min(Config.CHOICE_EXPORT_TO_MC, choices.length - 1));

        mcOptsCombo = new JComboBox(choices);
        mcOptsCombo.setSelectedIndex(Config.CHOICE_EXPORT_TO_MC);
        packMeta = new JComboBox(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "140", "141", "142", "143", "144", "145", "146", "147", "148", "149", "150", "151", "152", "153", "154", "155", "156", "157", "158", "159", "160", "161", "162", "163", "164", "165", "166", "167", "168", "169", "170", "171", "172", "173", "174", "175", "176", "177", "178", "179", "180", "181", "182", "183", "184", "185", "186", "187", "188", "189", "190", "191", "192", "193", "194", "195", "196", "197", "198", "199", "200", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220", "221", "222", "223", "224", "225", "226", "227", "228", "229", "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240", "241", "242", "243", "244", "245", "246", "247", "248", "249", "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280", "281", "282", "283", "284", "285", "286", "287", "288", "289", "290", "291", "292", "293", "294", "295", "296", "297", "298", "299", "300", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311", "312", "313", "314", "315", "316", "317", "318", "319", "320", "321", "322", "323", "324", "325", "326", "327", "328", "329", "330", "331", "332", "333", "334", "335", "336", "337", "338", "339", "340", "341", "342", "343", "344", "345", "346", "347", "348", "349", "350", "351", "352", "353", "354", "355", "356", "357", "358", "359", "360", "361", "362", "363", "364", "365", "366", "367", "368", "369", "370", "371", "372", "373", "374", "375", "376", "377", "378", "379", "380", "381", "382", "383", "384", "385", "386", "387", "388", "389", "390", "391", "392", "393", "394", "395", "396", "397", "398", "399", "400", "401", "402", "403", "404", "405", "406", "407", "408", "409", "410", "411", "412", "413", "414", "415", "416", "417", "418", "419", "420", "421", "422", "423", "424", "425", "426", "427", "428", "429", "430", "431", "432", "433", "434", "435", "436", "437", "438", "439", "440", "441", "442", "443", "444", "445", "446", "447", "448", "449", "450", "451", "452", "453", "454", "455", "456", "457", "458", "459", "460", "461", "462", "463", "464", "465", "466", "467", "468", "469", "470", "471", "472", "473", "474", "475", "476", "477", "478", "479", "480", "481", "482", "483", "484", "485", "486", "487", "488", "489", "490", "491", "492", "493", "494", "495", "496", "497", "498", "499", "500"});
        packMeta.setSelectedIndex(SequenceExportProject.getPackMetaNumber() - 1);
        unZip = Gui.checkbox(false);

        vbox.springForm(new String[]{"Resourcepack Name:", "Resourcepack Description:", "Export To Minecraft:"}, new JComponent[]{nameField, descriptionField, exportToMc});

        filepicker = new FileInput(this, "Select folder to export to...", Config.FilePath.EXPORT, "Select folder to export to", FileChooser.FOLDERS, true);
        filepicker.setEnabled(false);
        vbox.add(filepicker);

        vbox.springForm(new String[]{"Resourcepack Format (Refer to <a href=\"https://minecraft.wiki/w/Pack_format#List_of_resource_pack_formats\">this</a> if you don't know):", "In Minecraft:", "Unzip:"}, new JComponent[]{packMeta, mcOptsCombo, unZip});

        vbox.gapl();

        buttonOK = new JButton("Export", Icons.MENU_EXPORT);
        buttonCancel = new JButton("Cancel", Icons.MENU_CANCEL);
        vbox.buttonRow(Gui.RIGHT, buttonOK, buttonCancel);

        return vbox;
    }


    @Override
    protected void addActions() {
        setEnterButton(buttonOK);

        buttonCancel.addActionListener(closeListener);

        buttonOK.addActionListener(exportListener);

        exportToMc.addItemListener(exportToMcListener);
    }


    private List<String> getOptions() {
        final List<File> aList = FileUtils.listDirectory(OsUtils.getMcDir("resourcepacks"));
        final List<String> options = new ArrayList<String>();

        for (final File f : aList) {
            if (f.isDirectory()) continue;
            final String[] parts = FileUtils.getFilenameParts(f);

            if (parts[1].equalsIgnoreCase("zip")) {
                options.add(parts[0]);
            }
        }

        Collections.sort(options);

        return options;
    }


    @Override
    protected void onShown() {
        nameField.setText(Projects.getActive().getTitle());
        descriptionField.setText(Projects.getActive().getDescription());
    }

    private final ActionListener exportListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent evt) {
            Projects.getActive().setExportPackVersion(packMeta.getSelectedIndex() + 1);
            Projects.getActive().setUnZip(unZip.isSelected());
            Projects.getActive().setTitle(nameField.getText());
            Projects.getActive().setDescription(descriptionField.getText());

            final String name = nameField.getText().trim();
            if (name.length() == 0) {
                Alerts.error(self(), "Invalid name", "Missing file name!");
                return;
            }

            if (installedPackNames.contains(name)) {
                //@formatter:off
                final boolean overwrite = Alerts.askYesNo(
                        App.getFrame(),
                        "File Exists",
                        "File named \"" + name + ".zip\" already exists in the output folder.\n" +
                                "Do you want to overwrite it?"
                );
                //@formatter:on

                if (!overwrite) return;

            }
            File file = null;
            if (exportToMc.isSelected()) {
                file = OsUtils.getMcDir("resourcepacks/" + name + ".zip");
            } else if (filepicker.getFile() != null){
                file = new File(filepicker.getFile().getPath() + "/" + name + ".zip");
            }

            try {
                closeDialog();

                if(file != null){
                Tasks.taskExportProject(file, new Runnable() {

                    @Override
                    public void run() {
                        // 0 - replace
                        // 1 - put on top
                        // 2 - don't change settings

                        final int choice = Config.CHOICE_EXPORT_TO_MC = mcOptsCombo.getSelectedIndex();
                        Config.save();

                        if (choice == MC_NO_CHANGE) return;

                        // TODO take action based on choice

                        // set as default now.

                        final File f = OsUtils.getMcDir("options.txt");
                        if (!f.exists()) {
                            Log.w("MC options file not found.");
                            return;
                        }

                        try {
                            final List<String> lines = SimpleConfig.listFromFile(f);

                            boolean a = false, b = false;

                            final String fname = name + ".zip";

                            final String optOld = "skin:" + fname;

                            final String optNew = "resourcePacks:[" + Const.GSON_UGLY.toJson(fname) + "]";

                            for (int i = 0; i < lines.size(); i++) {
                                // 1.6-
                                if (lines.get(i).startsWith("skin:")) {
                                    a = true;
                                    Log.f3("Writing to MC options: " + optOld);
                                    lines.set(i, optOld);
                                } else
                                    // 1.7+
                                    if (lines.get(i).startsWith("resourcePacks:")) {
                                        if (choice == MC_ADD) {
                                            try {
                                                String orig = lines.get(i).substring("resourcePacks:".length());
                                                orig = orig.trim();

                                                final List<String> list = Const.GSON.fromJson(orig, new TypeToken<List<String>>() {
                                                }.getType());

                                                list.remove(fname);
                                                list.add(0, fname);

                                                final String packs_new = Const.GSON_UGLY.toJson(list);

                                                Log.f3("Writing to MC options: " + packs_new);

                                                lines.set(i, "resourcePacks:" + packs_new);
                                                b = true;
                                            } catch (final Exception e) {
                                                Log.e(e);
                                            }
                                        }

                                        if (!b || choice == MC_ALONE) {
                                            lines.set(i, optNew);
                                            Log.f3("Writing to MC options: " + optNew);
                                            b = true;
                                        }
                                    }
                            }

                            // add the unused one (make sure both will be
                            // present when MC starts)
                            if (!b) lines.add(optNew);
                            if (!a) lines.add(optOld);

                            SimpleConfig.listToFile(f, lines);
                            Log.i("Minecraft config file was changed.");

                        } catch (final IOException e) {
                            Log.e(e);
                        }
                    }
                });

            }} catch (final Exception e) {
                Alerts.error(self(), "An error occured while exporting.");
                Log.e(e);
            }
        }
    };

    private final ItemListener exportToMcListener = new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                mcOptsCombo.setEnabled(true);
                filepicker.setEnabled(false);
            } else {
                mcOptsCombo.setEnabled(false);
                filepicker.setEnabled(true);
            }
        }

    };

}
