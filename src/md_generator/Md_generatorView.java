/*
 * Md_generator_View.java
 */

package md_generator;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * The application's main frame.
 */

public class Md_generatorView extends FrameView
{
    String insn_info,default_info;
    Runtime load = Runtime.getRuntime();

    public Md_generatorView(SingleFrameApplication app)
    {
        super(app);
        initComponents();

        default_info = "Hover the mouse over instruction boxes for more information.\n SI: Single Integer (4 bytes) \n QI: Quarter Integer (1 byte)\n HI: Half Integer (2 bytes)";
        info_text_area.setText(default_info);
        insn_type_tab_pane.setSelectedIndex(1);
    }

    // Method to select all insns with SI mode
    @Action
    public void select_all_si()
    {
        add_check.setSelected(true);    add_si_check.setSelected(true);
        sub_check.setSelected(true);    sub_si_check.setSelected(true);
        div_check.setSelected(true);    div_si_check.setSelected(true);
        mult_check.setSelected(true);   mult_si_check.setSelected(true);
        abs_check.setSelected(true);    abs_si_check.setSelected(true);
        mod_check.setSelected(true);    mod_si_check.setSelected(true);
        umod_check.setSelected(true);   umod_si_check.setSelected(true);
        and_check.setSelected(true);    and_si_check.setSelected(true);
        or_check.setSelected(true);     or_si_check.setSelected(true);
        xor_check.setSelected(true);    xor_si_check.setSelected(true);
        ashl_check.setSelected(true);   ashl_si_check.setSelected(true);
        ashr_check.setSelected(true);   ashr_si_check.setSelected(true);
        mov_check.setSelected(true);    mov_si_check.setSelected(true);
        load_check.setSelected(true);   load_si_check.setSelected(true);
        store_check.setSelected(true);  store_si_check.setSelected(true);
    }

    // Method to select all insns with QI mode
    @Action
    public void select_all_qi()
    {
        add_check.setSelected(true);   add_qi_check.setSelected(true);
        sub_check.setSelected(true);   sub_qi_check.setSelected(true);
        div_check.setSelected(true);   div_qi_check.setSelected(true);
        mult_check.setSelected(true);  mult_qi_check.setSelected(true);
        abs_check.setSelected(true);   abs_qi_check.setSelected(true);
        mod_check.setSelected(true);   mod_qi_check.setSelected(true);
        umod_check.setSelected(true);  umod_qi_check.setSelected(true);
        and_check.setSelected(true);   and_qi_check.setSelected(true);
        or_check.setSelected(true);    or_qi_check.setSelected(true);
        xor_check.setSelected(true);   xor_qi_check.setSelected(true);
        ashl_check.setSelected(true);  ashl_qi_check.setSelected(true);
        ashr_check.setSelected(true);  ashr_qi_check.setSelected(true);
        mov_check.setSelected(true);   mov_qi_check.setSelected(true);
        load_check.setSelected(true);  load_qi_check.setSelected(true);
        store_check.setSelected(true); store_qi_check.setSelected(true);
    }

    // Method to select all insns with HI mode
    @Action
    public void select_all_hi()
    {
        add_check.setSelected(true);    add_hi_check.setSelected(true);
        sub_check.setSelected(true);    sub_hi_check.setSelected(true);
        div_check.setSelected(true);    div_hi_check.setSelected(true);
        mult_check.setSelected(true);   mult_hi_check.setSelected(true);
        abs_check.setSelected(true);    abs_hi_check.setSelected(true);
        mod_check.setSelected(true);    mod_hi_check.setSelected(true);
        umod_check.setSelected(true);   umod_hi_check.setSelected(true);
        and_check.setSelected(true);    and_hi_check.setSelected(true);
        or_check.setSelected(true);     or_hi_check.setSelected(true);
        xor_check.setSelected(true);    xor_hi_check.setSelected(true);
        ashl_check.setSelected(true);   ashl_hi_check.setSelected(true);
        ashr_check.setSelected(true);   ashr_hi_check.setSelected(true);
        mov_check.setSelected(true);    mov_hi_check.setSelected(true);
        load_check.setSelected(true);   load_hi_check.setSelected(true);
        store_check.setSelected(true);  store_hi_check.setSelected(true);
    }

    @Action
    public void clear_all()
    {
        add_check.setSelected(false);        sub_check.setSelected(false);
        div_check.setSelected(false);        mult_check.setSelected(false);
        abs_check.setSelected(false);        mod_check.setSelected(false);
        umod_check.setSelected(false);       and_check.setSelected(false);
        or_check.setSelected(false);         xor_check.setSelected(false);
        ashl_check.setSelected(false);       ashr_check.setSelected(false);
        mov_check.setSelected(false);        load_check.setSelected(false);
        store_check.setSelected(false);      epilogue_check.setSelected(false);
        prologue_check.setSelected(false);   call_check.setSelected(false);
        call_value_check.setSelected(false);
    }

    //Calls core generator, passing Instrusction SPN, No. of Operands and Constraint types.
    @Action
    public void gen() throws IOException
    {
        String tmp="";  //String that holds current instruction pattern.
        String md_file=";;-------------------------------------------------------------------------";
        md_file=md_file+"\n;;This is an autogenerated Machine Descriptor file by MD Generator for GCC";    //String to be written in md file.
        md_file=md_file+"\n;;-------------------------------------------------------------------------";
        gen_core core=new gen_core();

        //Check for each check box and call generator.
        if(add_check.isSelected() && (add_si_check.isSelected() || add_qi_check.isSelected() || add_hi_check.isSelected()))
        {
            md_file=md_file+"\n\n;;--------------------------------";
            md_file=md_file+"\n;;Arithmetic Instruction Patterns:";
            md_file=md_file+"\n;;--------------------------------";
            if(add_si_check.isSelected())
            {
                tmp=core.gen_core(add_check.getActionCommand(),"si");
                core.flush();           //Flush all core data.
                md_file=md_file+"\n\n"+tmp;
            }
            if(add_qi_check.isSelected())
            {
                tmp=core.gen_core(add_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(add_hi_check.isSelected())
            {
                tmp=core.gen_core(add_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(sub_check.isSelected()  && (sub_si_check.isSelected() || sub_qi_check.isSelected() || sub_hi_check.isSelected()))
        {
            if(sub_si_check.isSelected())
            {
                tmp=core.gen_core(sub_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(sub_qi_check.isSelected())
            {
                tmp=core.gen_core(sub_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(sub_hi_check.isSelected())
            {
                tmp=core.gen_core(sub_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(div_check.isSelected() && (div_si_check.isSelected() || div_qi_check.isSelected() || div_hi_check.isSelected()))
        {
            if(div_si_check.isSelected())
            {
                tmp=core.gen_core(div_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(div_qi_check.isSelected())
            {
                tmp=core.gen_core(div_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(div_hi_check.isSelected())
            {
                tmp=core.gen_core(div_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(mult_check.isSelected() && (mult_si_check.isSelected() || mult_qi_check.isSelected() || mult_hi_check.isSelected()))
        {
            if(mult_si_check.isSelected())
            {
                tmp=core.gen_core(mult_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(mult_qi_check.isSelected())
            {
                tmp=core.gen_core(mult_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(mult_hi_check.isSelected())
            {
                tmp=core.gen_core(mult_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(abs_check.isSelected() && (abs_si_check.isSelected() || abs_qi_check.isSelected() || abs_hi_check.isSelected()))
        {
            if(abs_si_check.isSelected())
            {
                tmp=core.gen_core(abs_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(abs_qi_check.isSelected())
            {
                tmp=core.gen_core(abs_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(abs_hi_check.isSelected())
            {
                tmp=core.gen_core(abs_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(mod_check.isSelected() && (mod_si_check.isSelected() || mod_qi_check.isSelected() || mod_hi_check.isSelected()))
        {
            if(mod_si_check.isSelected())
            {
                tmp=core.gen_core(mod_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(mod_qi_check.isSelected())
            {
                tmp=core.gen_core(mod_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(mod_hi_check.isSelected())
            {
                tmp=core.gen_core(mod_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(umod_check.isSelected() && (umod_si_check.isSelected() || umod_qi_check.isSelected() || umod_hi_check.isSelected()))
        {
            if(umod_si_check.isSelected())
            {
                tmp=core.gen_core(umod_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(umod_qi_check.isSelected())
            {
                tmp=core.gen_core(umod_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(umod_hi_check.isSelected())
            {
                tmp=core.gen_core(umod_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(and_check.isSelected() && (and_si_check.isSelected() || and_qi_check.isSelected() || and_hi_check.isSelected()))
        {
            md_file=md_file+"\n\n;;-----------------------------";
            md_file=md_file+"\n;;Logical Instruction Patterns:";
            md_file=md_file+"\n;;-----------------------------";
            if(and_si_check.isSelected())
            {
                tmp=core.gen_core(and_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(and_qi_check.isSelected())
            {
                tmp=core.gen_core(and_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(and_hi_check.isSelected())
            {
                tmp=core.gen_core(and_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(or_check.isSelected() && (or_si_check.isSelected() || or_qi_check.isSelected() || or_hi_check.isSelected()))
        {
            if(or_si_check.isSelected())
            {
                tmp=core.gen_core(or_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(or_qi_check.isSelected())
            {
                tmp=core.gen_core(or_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(or_hi_check.isSelected())
            {
                tmp=core.gen_core(or_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(xor_check.isSelected() && (xor_si_check.isSelected() || xor_qi_check.isSelected() || xor_hi_check.isSelected()))
        {
            if(xor_si_check.isSelected())
            {
                tmp=core.gen_core(xor_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(xor_qi_check.isSelected())
            {
                tmp=core.gen_core(xor_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(xor_hi_check.isSelected())
            {
                tmp=core.gen_core(xor_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(ashl_check.isSelected() && (ashl_si_check.isSelected() || ashl_qi_check.isSelected() || ashl_hi_check.isSelected()))
        {
            if(ashl_si_check.isSelected())
            {
                tmp=core.gen_core(ashl_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(ashl_qi_check.isSelected())
            {
                tmp=core.gen_core(ashl_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(ashl_hi_check.isSelected())
            {
                tmp=core.gen_core(ashl_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(ashr_check.isSelected() && (ashr_si_check.isSelected() || ashr_qi_check.isSelected() || ashr_hi_check.isSelected()))
        {
            if(ashr_si_check.isSelected())
            {
                tmp=core.gen_core(ashr_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(ashr_qi_check.isSelected())
            {
                tmp=core.gen_core(ashr_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(ashr_hi_check.isSelected())
            {
                tmp=core.gen_core(ashr_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(mov_check.isSelected() && (mov_si_check.isSelected() || mov_qi_check.isSelected() || mov_hi_check.isSelected()))
        {
            md_file=md_file+"\n\n;;-----------------------------------";
            md_file=md_file+"\n;;Data Transfer Instruction Patterns:";
            md_file=md_file+"\n;;-----------------------------------";
            if(mov_si_check.isSelected())
            {
                tmp=core.gen_core(mov_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(mov_qi_check.isSelected())
            {
                tmp=core.gen_core(mov_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(mov_hi_check.isSelected())
            {
                tmp=core.gen_core(mov_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(load_check.isSelected()  && (load_si_check.isSelected() || load_qi_check.isSelected() || load_hi_check.isSelected()))
        {
            if(load_si_check.isSelected())
            {
                tmp=core.gen_core(load_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(load_qi_check.isSelected())
            {
                tmp=core.gen_core(load_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(load_hi_check.isSelected())
            {
                tmp=core.gen_core(load_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        if(store_check.isSelected() && (store_si_check.isSelected() || store_qi_check.isSelected() || store_hi_check.isSelected()))
        {
            if(store_si_check.isSelected())
            {
                tmp=core.gen_core(store_check.getActionCommand(),"si");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(store_qi_check.isSelected())
            {
                tmp=core.gen_core(store_check.getActionCommand(),"qi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
            if(store_hi_check.isSelected())
            {
                tmp=core.gen_core(store_check.getActionCommand(),"hi");
                core.flush();
                md_file=md_file+"\n\n"+tmp;
            }
        }
        //Jump and Functional instructions are treated specially.
        if(jump_check.isSelected())
        {
            md_file=md_file+"\n\n;;--------------------------";
            md_file=md_file+"\n;;Jump Instruction Patterns:";
            md_file=md_file+"\n;;--------------------------";
            tmp="(define_insn \"jump\"\n"+
                    "\t[(set (pc) (label_ref (match_operand 0 \"\" \"\")))]\n"+
                    "\t\"\"\n"+
                    "\t{\n"+
                        "\treturn \"j \\t%l0;\n"+
                    "\t})";
            md_file=md_file+"\n\n"+tmp;
        }
        if(ijump_check.isSelected())
        {
            tmp="(define_insn \"indirect_jump\"\n"+
                "\t[(set (pc) (match_operand:SI 0 \"register_operand\" \"\"))]\n"+
                "\t\"\"\n"+
                "\t\"jr \\t%0\\\n"+
                "\t[(set_attr \"type\" \"tbranch\")])";
            md_file=md_file+"\n\n"+tmp;
        }
        if(epilogue_check.isSelected())
        {
            md_file=md_file+"\n\n;;--------------------------------";
            md_file=md_file+"\n;;Functional Instruction Patterns:";
            md_file=md_file+"\n;;--------------------------------";
            tmp="(define_expand \"epilogue\"\n"+
                "\t[(clobber (const_int 0))]\n"+
                "\t\"\"\n"+
                "\t{\n"+
                    "\tspim_epilogue();\n"+
                    "\tDONE;\n"+
                "\t})";
            md_file=md_file+"\n\n"+tmp;
        }
        if(prologue_check.isSelected())
        {
            tmp="(define_expand \"prologue\"\n"+
                "\t[(clobber (const_int 0))]\n"+
                "\t\"\"\n"+
                "\t{\n"+
                    "\tspim_prologue();\n"+
                    "\tDONE;\n"+
                "\t})";
            md_file=md_file+"\n\n"+tmp;
        }
        if(call_check.isSelected())
        {
            tmp="(define_insn \"call\"\n"+
                "\t[(call (match_operand:SI 0 \"memory_operand\" \"m\")\n"+
	        "\t(match_operand:SI 1 \"immediate_operand\" \"i\"))\n"+
                "\t(clobber (reg:SI 31))\n"+
                "\t]\n"+
                "\t\"\"\n"+
                "\t\"*\n"+
		"\treturn emit_asm_call(operands,0);)";
            md_file=md_file+"\n\n"+tmp;
        }
        if(call_value_check.isSelected())
        {
            tmp="(define_insn \"call_value\"\n"+
                "\t[(set (match_operand:SI 0 \"register_operand\" \"=r\")\n"+
                "\t(call (match_operand:SI 1 \"memory_operand\" \"m\")\n"+
		"\t(match_operand:SI 2 \"immediate_operand\" \"i\")))\n"+
                "\t(clobber (reg:SI 31))\n"+
                "\t]\n"+
                "\t\"\"\n"+
                "\t\"*\n"+
		"\treturn emit_asm_call(operands,1);)";
            md_file=md_file+"\n\n"+tmp;
        }
        //Write to md file.
        try
        {
            FileWriter fstream = new FileWriter("e:\\spim\\spim.md");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(md_file);
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        //Open md file in notepad++
        load.exec("C:\\Program Files (x86)\\Notepad++\\notepad++.exe e:\\spim\\spim.md");
    }

    public static int i=0;
    @Action
    public void NextActionPerformed(java.awt.event.ActionEvent evt)
    {
        i++;
        insn_type_tab_pane.setSelectedIndex(i);
    }

    @Action
    public void BackActionPerformed(java.awt.event.ActionEvent evt)
    {
        i--;
        insn_type_tab_pane.setSelectedIndex(i);
    }

    //Shows About Window.
    @Action
    public void showAboutBox()
    {
        if (aboutBox == null)
        {
            JFrame mainFrame = Md_generatorApp.getApplication().getMainFrame();
            aboutBox = new Md_generatorAboutBox(mainFrame);
            aboutBox.setResizable(false);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        Md_generatorApp.getApplication().show(aboutBox);
    }

    //Generates C file.
    @Action
    public void copy_c_file() throws IOException
    {
        String source="e:\\spim iit\\spim5.c";
        String destination="e:\\spim\\";

        FileChannel in = null;
        FileChannel out = null;

        try
        {
            in = new FileInputStream(source).getChannel();
            File outFile = new File(destination, "spim.c");
            out = new FileOutputStream(outFile).getChannel();
            in.transferTo(0, in.size(), out);
        }
        catch (IOException ex)
        {
                Logger.getLogger(Md_generatorView.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
        load.exec("C:\\Program Files (x86)\\Notepad++\\notepad++.exe e:\\spim\\spim.c");
    }
    @Action
    public void gen_h_file() throws IOException
    {
        String fixed_reg="#define FIXED_REGISTERS\n{1,1", caller_reg="#define CALL_USED_REGISTERS\n{1,1";
        int fixed_count=0,caller_count=0,callee_count=0;
        if(jComboBox1.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox1.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox2.getSelectedIndex()==0)
        {
            fixed_reg+=",1 ";
            caller_reg+=",0 ";
            fixed_count++;
        }
        else if(jComboBox2.getSelectedIndex()==1)
        {
            fixed_reg+=",0 ";
            caller_reg+=",1 ";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0 ";
            caller_reg+=",0 ";
            callee_count++;
        }
        if(jComboBox3.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox3.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox4.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox4.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox5.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox5.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox6.getSelectedIndex()==0)
        {
            fixed_reg+=",1,\n ";
            caller_reg+=",0,\n ";
            fixed_count++;
        }
        else if(jComboBox6.getSelectedIndex()==1)
        {
            fixed_reg+=",0,\n ";
            caller_reg+=",1,\n ";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0,\n ";
            caller_reg+=",0,\n ";
            callee_count++;
        }
        if(jComboBox7.getSelectedIndex()==0)
        {
            fixed_reg+="1";
            caller_reg+="0";
            fixed_count++;
        }
        else if(jComboBox7.getSelectedIndex()==1)
        {
            fixed_reg+="0";
            caller_reg+="1";
            caller_count++;
        }
        else
        {
            fixed_reg+="0";
            caller_reg+="0";
            callee_count++;
        }
        if(jComboBox8.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox8.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox9.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox9.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox10.getSelectedIndex()==0)
        {
            fixed_reg+=",1 ";
            caller_reg+=",0 ";
            fixed_count++;
        }
        else if(jComboBox10.getSelectedIndex()==1)
        {
            fixed_reg+=",0 ";
            caller_reg+=",1 ";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0 ";
            caller_reg+=",0 ";
            callee_count++;
        }
        if(jComboBox11.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox11.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox12.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox12.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox13.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox13.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox14.getSelectedIndex()==0)
        {
            fixed_reg+=",1,\n ";
            caller_reg+=",0,\n ";
            fixed_count++;
        }
        else if(jComboBox14.getSelectedIndex()==1)
        {
            fixed_reg+=",0,\n ";
            caller_reg+=",1,\n ";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0,\n ";
            caller_reg+=",0,\n ";
            callee_count++;
        }
        if(jComboBox15.getSelectedIndex()==0)
        {
            fixed_reg+="1";
            caller_reg+="0";
            fixed_count++;
        }
        else if(jComboBox15.getSelectedIndex()==1)
        {
            fixed_reg+="0";
            caller_reg+="1";
            caller_count++;
        }
        else
        {
            fixed_reg+="0";
            caller_reg+="0";
            callee_count++;
        }
        if(jComboBox16.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox16.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox17.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox17.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox18.getSelectedIndex()==0)
        {
            fixed_reg+=",1 ";
            caller_reg+=",0 ";
            fixed_count++;
        }
        else if(jComboBox18.getSelectedIndex()==1)
        {
            fixed_reg+=",0 ";
            caller_reg+=",1 ";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0 ";
            caller_reg+=",0 ";
            callee_count++;
        }
        if(jComboBox19.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox19.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox20.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox20.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox21.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox21.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        if(jComboBox22.getSelectedIndex()==0)
        {
            fixed_reg+=",1,\n ";
            caller_reg+=",0,\n ";
            fixed_count++;
        }
        else if(jComboBox22.getSelectedIndex()==1)
        {
            fixed_reg+=",0,\n ";
            caller_reg+=",1,\n ";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0,\n ";
            caller_reg+=",0,\n ";
            callee_count++;
        }
        if(jComboBox23.getSelectedIndex()==0)
        {
            fixed_reg+="1";
            caller_reg+="0";
            fixed_count++;
        }
        else if(jComboBox23.getSelectedIndex()==1)
        {
            fixed_reg+="0";
            caller_reg+="1";
            caller_count++;
        }
        else
        {
            fixed_reg+="0";
            caller_reg+="0";
            callee_count++;
        }
        if(jComboBox24.getSelectedIndex()==0)
        {
            fixed_reg+=",1";
            caller_reg+=",0";
            fixed_count++;
        }
        else if(jComboBox24.getSelectedIndex()==1)
        {
            fixed_reg+=",0";
            caller_reg+=",1";
            caller_count++;
        }
        else
        {
            fixed_reg+=",0";
            caller_reg+=",0";
            callee_count++;
        }
        fixed_reg+=",1,1 ,1,1,1,1}";
        caller_reg+=",1,1 ,1,1,1,1}";
        try
        {
            File file = new File("e:\\spim\\spim.h");
            RandomAccessFile rand = new RandomAccessFile(file,"rw");
            int l=(int)rand.length();
            rand.seek(l);  //Seek to start point of file
            rand.writeBytes("\n\n");
            rand.writeBytes(fixed_reg);
            rand.writeBytes("\n\n");
            rand.writeBytes(caller_reg);
            rand.close();

            /*
            FileWriter fstream = new FileWriter("e:\\spim\\spim.h");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(fixed_reg);
            out.write("\n\n");
            out.write(caller_reg);
            out.close();*/
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        load.exec("C:\\Program Files (x86)\\Notepad++\\notepad++.exe e:\\spim\\spim.h");
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        insn_type_tab_pane = new javax.swing.JTabbedPane();
        arithmetic_insn_tab = new javax.swing.JLayeredPane();
        add_pane = new javax.swing.JLayeredPane();
        add_check = new javax.swing.JCheckBox();
        add_si_check = new javax.swing.JCheckBox();
        add_qi_check = new javax.swing.JCheckBox();
        add_hi_check = new javax.swing.JCheckBox();
        arithmetic_label = new javax.swing.JLabel();
        sub_pane = new javax.swing.JLayeredPane();
        sub_check = new javax.swing.JCheckBox();
        sub_si_check = new javax.swing.JCheckBox();
        sub_qi_check = new javax.swing.JCheckBox();
        sub_hi_check = new javax.swing.JCheckBox();
        div_pane = new javax.swing.JLayeredPane();
        div_check = new javax.swing.JCheckBox();
        div_si_check = new javax.swing.JCheckBox();
        div_qi_check = new javax.swing.JCheckBox();
        div_hi_check = new javax.swing.JCheckBox();
        mult_pane = new javax.swing.JLayeredPane();
        mult_check = new javax.swing.JCheckBox();
        mult_si_check = new javax.swing.JCheckBox();
        mult_qi_check = new javax.swing.JCheckBox();
        mult_hi_check = new javax.swing.JCheckBox();
        abs_pane = new javax.swing.JLayeredPane();
        abs_check = new javax.swing.JCheckBox();
        abs_si_check = new javax.swing.JCheckBox();
        abs_qi_check = new javax.swing.JCheckBox();
        abs_hi_check = new javax.swing.JCheckBox();
        mod_pane = new javax.swing.JLayeredPane();
        mod_check = new javax.swing.JCheckBox();
        mod_si_check = new javax.swing.JCheckBox();
        mod_qi_check = new javax.swing.JCheckBox();
        mod_hi_check = new javax.swing.JCheckBox();
        umod_pane = new javax.swing.JLayeredPane();
        umod_check = new javax.swing.JCheckBox();
        umod_si_check = new javax.swing.JCheckBox();
        umod_qi_check = new javax.swing.JCheckBox();
        umod_hi_check = new javax.swing.JCheckBox();
        arithmetic_separator = new javax.swing.JSeparator();
        next = new javax.swing.JButton();
        logical_insn_tab = new javax.swing.JLayeredPane();
        logical_label = new javax.swing.JLabel();
        and_pane = new javax.swing.JLayeredPane();
        and_check = new javax.swing.JCheckBox();
        and_si_check = new javax.swing.JCheckBox();
        and_qi_check = new javax.swing.JCheckBox();
        and_hi_check = new javax.swing.JCheckBox();
        or_pane = new javax.swing.JLayeredPane();
        or_check = new javax.swing.JCheckBox();
        or_si_check = new javax.swing.JCheckBox();
        or_qi_check = new javax.swing.JCheckBox();
        or_hi_check = new javax.swing.JCheckBox();
        xor_pane = new javax.swing.JLayeredPane();
        xor_check = new javax.swing.JCheckBox();
        xor_si_check = new javax.swing.JCheckBox();
        xor_qi_check = new javax.swing.JCheckBox();
        xor_hi_check = new javax.swing.JCheckBox();
        ashl_pane = new javax.swing.JLayeredPane();
        ashl_check = new javax.swing.JCheckBox();
        ashl_si_check = new javax.swing.JCheckBox();
        ashl_qi_check = new javax.swing.JCheckBox();
        ashl_hi_check = new javax.swing.JCheckBox();
        ashr_pane = new javax.swing.JLayeredPane();
        ashr_check = new javax.swing.JCheckBox();
        ashr_si_check = new javax.swing.JCheckBox();
        ashr_qi_check = new javax.swing.JCheckBox();
        ashr_hi_check = new javax.swing.JCheckBox();
        logical_separator = new javax.swing.JSeparator();
        next1 = new javax.swing.JButton();
        back = new javax.swing.JButton();
        data_transfer_insn_tab = new javax.swing.JLayeredPane();
        mov_pane = new javax.swing.JLayeredPane();
        mov_check = new javax.swing.JCheckBox();
        mov_si_check = new javax.swing.JCheckBox();
        mov_qi_check = new javax.swing.JCheckBox();
        mov_hi_check = new javax.swing.JCheckBox();
        data_label = new javax.swing.JLabel();
        load_pane = new javax.swing.JLayeredPane();
        load_check = new javax.swing.JCheckBox();
        load_si_check = new javax.swing.JCheckBox();
        load_qi_check = new javax.swing.JCheckBox();
        load_hi_check = new javax.swing.JCheckBox();
        store_pane = new javax.swing.JLayeredPane();
        store_check = new javax.swing.JCheckBox();
        store_si_check = new javax.swing.JCheckBox();
        store_qi_check = new javax.swing.JCheckBox();
        store_hi_check = new javax.swing.JCheckBox();
        data_transfer_separator = new javax.swing.JSeparator();
        next2 = new javax.swing.JButton();
        back2 = new javax.swing.JButton();
        jump_insn_tab = new javax.swing.JLayeredPane();
        jump_pane = new javax.swing.JLayeredPane();
        jump_check = new javax.swing.JCheckBox();
        ijump_pane = new javax.swing.JLayeredPane();
        ijump_check = new javax.swing.JCheckBox();
        epilogue_pane = new javax.swing.JLayeredPane();
        epilogue_check = new javax.swing.JCheckBox();
        prologue_pane = new javax.swing.JLayeredPane();
        prologue_check = new javax.swing.JCheckBox();
        jump_label = new javax.swing.JLabel();
        call_pane = new javax.swing.JLayeredPane();
        call_check = new javax.swing.JCheckBox();
        call_value__pane = new javax.swing.JLayeredPane();
        call_value_check = new javax.swing.JCheckBox();
        jump_separator = new javax.swing.JSeparator();
        back3 = new javax.swing.JButton();
        next3 = new javax.swing.JButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jComboBox21 = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jComboBox22 = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jComboBox23 = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jComboBox24 = new javax.swing.JComboBox();
        back4 = new javax.swing.JButton();
        info = new javax.swing.JScrollPane();
        info_text_area = new javax.swing.JTextArea();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        select_all_si = new javax.swing.JButton();
        select_all_qi = new javax.swing.JButton();
        select_all_hi = new javax.swing.JButton();
        clear_all = new javax.swing.JButton();
        gen_button_pane1 = new javax.swing.JLayeredPane();
        gen_c = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        gen_md = new javax.swing.JButton();
        gen_h = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        insn_type_tab_pane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(md_generator.Md_generatorApp.class).getContext().getResourceMap(Md_generatorView.class);
        insn_type_tab_pane.setToolTipText(resourceMap.getString("insn_type_tab_pane.toolTipText")); // NOI18N
        insn_type_tab_pane.setName("insn_type_tab_pane"); // NOI18N

        arithmetic_insn_tab.setBackground(resourceMap.getColor("arithmetic_insn_tab.background")); // NOI18N
        arithmetic_insn_tab.setToolTipText(resourceMap.getString("arithmetic_insn_tab.toolTipText")); // NOI18N
        arithmetic_insn_tab.setName("arithmetic_insn_tab"); // NOI18N

        add_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        add_pane.setName("add_pane"); // NOI18N
        add_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_paneadd_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_panedefault_info(evt);
            }
        });

        add_check.setBackground(resourceMap.getColor("add_check.background")); // NOI18N
        add_check.setText(resourceMap.getString("add_check.text")); // NOI18N
        add_check.setToolTipText(resourceMap.getString("add_check.toolTipText")); // NOI18N
        add_check.setActionCommand(resourceMap.getString("add_check.actionCommand")); // NOI18N
        add_check.setName("add_check"); // NOI18N
        add_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_checkadd_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_checkdefault_info(evt);
            }
        });
        add_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                add_checkadd_change(evt);
            }
        });
        add_check.setBounds(20, 10, 110, -1);
        add_pane.add(add_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        add_si_check.setBackground(resourceMap.getColor("add_si_check.background")); // NOI18N
        add_si_check.setText(resourceMap.getString("add_si_check.text")); // NOI18N
        add_si_check.setToolTipText(resourceMap.getString("add_si_check.toolTipText")); // NOI18N
        add_si_check.setActionCommand(resourceMap.getString("add_si_check.actionCommand")); // NOI18N
        add_si_check.setEnabled(false);
        add_si_check.setName("add_si_check"); // NOI18N
        add_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_si_checkadd_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_si_checkdefault_info(evt);
            }
        });
        add_si_check.setBounds(30, 40, 120, -1);
        add_pane.add(add_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        add_qi_check.setBackground(resourceMap.getColor("add_qi_check.background")); // NOI18N
        add_qi_check.setText(resourceMap.getString("add_qi_check.text")); // NOI18N
        add_qi_check.setToolTipText(resourceMap.getString("add_qi_check.toolTipText")); // NOI18N
        add_qi_check.setActionCommand(resourceMap.getString("add_qi_check.actionCommand")); // NOI18N
        add_qi_check.setEnabled(false);
        add_qi_check.setName("add_qi_check"); // NOI18N
        add_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_qi_checkadd_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_qi_checkdefault_info(evt);
            }
        });
        add_qi_check.setBounds(30, 60, 120, -1);
        add_pane.add(add_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        add_hi_check.setBackground(resourceMap.getColor("add_hi_check.background")); // NOI18N
        add_hi_check.setText(resourceMap.getString("add_hi_check.text")); // NOI18N
        add_hi_check.setToolTipText(resourceMap.getString("add_hi_check.toolTipText")); // NOI18N
        add_hi_check.setActionCommand(resourceMap.getString("add_hi_check.actionCommand")); // NOI18N
        add_hi_check.setEnabled(false);
        add_hi_check.setName("add_hi_check"); // NOI18N
        add_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                add_hi_checkadd_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                add_hi_checkdefault_info(evt);
            }
        });
        add_hi_check.setBounds(30, 80, 120, -1);
        add_pane.add(add_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        add_pane.setBounds(10, 40, 170, 120);
        arithmetic_insn_tab.add(add_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        arithmetic_label.setFont(resourceMap.getFont("arithmetic_label.font")); // NOI18N
        arithmetic_label.setText(resourceMap.getString("arithmetic_label.text")); // NOI18N
        arithmetic_label.setName("arithmetic_label"); // NOI18N
        arithmetic_label.setBounds(10, 10, 550, -1);
        arithmetic_insn_tab.add(arithmetic_label, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sub_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sub_pane.setName("sub_pane"); // NOI18N
        sub_pane.setPreferredSize(new java.awt.Dimension(100, 100));
        sub_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sub_panesub_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sub_panedefault_info(evt);
            }
        });

        sub_check.setBackground(resourceMap.getColor("sub_check.background")); // NOI18N
        sub_check.setText(resourceMap.getString("sub_check.text")); // NOI18N
        sub_check.setToolTipText(resourceMap.getString("sub_check.toolTipText")); // NOI18N
        sub_check.setActionCommand(resourceMap.getString("sub_check.actionCommand")); // NOI18N
        sub_check.setName("sub_check"); // NOI18N
        sub_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sub_checksub_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sub_checkdefault_info(evt);
            }
        });
        sub_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sub_checksub_change(evt);
            }
        });
        sub_check.setBounds(20, 10, 110, -1);
        sub_pane.add(sub_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sub_si_check.setBackground(resourceMap.getColor("sub_si_check.background")); // NOI18N
        sub_si_check.setText(resourceMap.getString("sub_si_check.text")); // NOI18N
        sub_si_check.setToolTipText(resourceMap.getString("sub_si_check.toolTipText")); // NOI18N
        sub_si_check.setActionCommand(resourceMap.getString("sub_si_check.actionCommand")); // NOI18N
        sub_si_check.setEnabled(false);
        sub_si_check.setName("sub_si_check"); // NOI18N
        sub_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sub_si_checksub_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sub_si_checkdefault_info(evt);
            }
        });
        sub_si_check.setBounds(30, 40, 120, -1);
        sub_pane.add(sub_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sub_qi_check.setBackground(resourceMap.getColor("sub_qi_check.background")); // NOI18N
        sub_qi_check.setText(resourceMap.getString("sub_qi_check.text")); // NOI18N
        sub_qi_check.setToolTipText(resourceMap.getString("sub_qi_check.toolTipText")); // NOI18N
        sub_qi_check.setActionCommand(resourceMap.getString("sub_qi_check.actionCommand")); // NOI18N
        sub_qi_check.setEnabled(false);
        sub_qi_check.setName("sub_qi_check"); // NOI18N
        sub_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sub_qi_checksub_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sub_qi_checkdefault_info(evt);
            }
        });
        sub_qi_check.setBounds(30, 60, 120, -1);
        sub_pane.add(sub_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sub_hi_check.setBackground(resourceMap.getColor("sub_hi_check.background")); // NOI18N
        sub_hi_check.setText(resourceMap.getString("sub_hi_check.text")); // NOI18N
        sub_hi_check.setToolTipText(resourceMap.getString("sub_hi_check.toolTipText")); // NOI18N
        sub_hi_check.setActionCommand(resourceMap.getString("sub_hi_check.actionCommand")); // NOI18N
        sub_hi_check.setEnabled(false);
        sub_hi_check.setName("sub_hi_check"); // NOI18N
        sub_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sub_hi_checksub_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sub_hi_checkdefault_info(evt);
            }
        });
        sub_hi_check.setBounds(30, 80, 120, -1);
        sub_pane.add(sub_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        sub_pane.setBounds(200, 40, 170, 120);
        arithmetic_insn_tab.add(sub_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        div_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        div_pane.setName("div_pane"); // NOI18N
        div_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                div_panediv_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                div_panedefault_info(evt);
            }
        });

        div_check.setBackground(resourceMap.getColor("div_check.background")); // NOI18N
        div_check.setText(resourceMap.getString("div_check.text")); // NOI18N
        div_check.setToolTipText(resourceMap.getString("div_check.toolTipText")); // NOI18N
        div_check.setActionCommand(resourceMap.getString("div_check.actionCommand")); // NOI18N
        div_check.setName("div_check"); // NOI18N
        div_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                div_checkdiv_info(evt);
            }
        });
        div_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                div_checkdiv_change(evt);
            }
        });
        div_check.setBounds(20, 10, 110, -1);
        div_pane.add(div_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        div_si_check.setBackground(resourceMap.getColor("div_si_check.background")); // NOI18N
        div_si_check.setText(resourceMap.getString("div_si_check.text")); // NOI18N
        div_si_check.setToolTipText(resourceMap.getString("div_si_check.toolTipText")); // NOI18N
        div_si_check.setActionCommand(resourceMap.getString("div_si_check.actionCommand")); // NOI18N
        div_si_check.setEnabled(false);
        div_si_check.setName("div_si_check"); // NOI18N
        div_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                div_si_checkdiv_info(evt);
            }
        });
        div_si_check.setBounds(30, 40, 120, -1);
        div_pane.add(div_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        div_qi_check.setBackground(resourceMap.getColor("div_qi_check.background")); // NOI18N
        div_qi_check.setText(resourceMap.getString("div_qi_check.text")); // NOI18N
        div_qi_check.setToolTipText(resourceMap.getString("div_qi_check.toolTipText")); // NOI18N
        div_qi_check.setActionCommand(resourceMap.getString("div_qi_check.actionCommand")); // NOI18N
        div_qi_check.setEnabled(false);
        div_qi_check.setName("div_qi_check"); // NOI18N
        div_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                div_qi_checkdiv_info(evt);
            }
        });
        div_qi_check.setBounds(30, 60, 120, -1);
        div_pane.add(div_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        div_hi_check.setBackground(resourceMap.getColor("div_hi_check.background")); // NOI18N
        div_hi_check.setText(resourceMap.getString("div_hi_check.text")); // NOI18N
        div_hi_check.setToolTipText(resourceMap.getString("div_hi_check.toolTipText")); // NOI18N
        div_hi_check.setActionCommand(resourceMap.getString("div_hi_check.actionCommand")); // NOI18N
        div_hi_check.setEnabled(false);
        div_hi_check.setName("div_hi_check"); // NOI18N
        div_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                div_hi_checkdiv_info(evt);
            }
        });
        div_hi_check.setBounds(30, 80, 120, -1);
        div_pane.add(div_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        div_pane.setBounds(390, 40, 170, 120);
        arithmetic_insn_tab.add(div_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mult_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mult_pane.setName("mult_pane"); // NOI18N
        mult_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mult_panemult_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mult_panedefault_info(evt);
            }
        });

        mult_check.setBackground(resourceMap.getColor("mult_check.background")); // NOI18N
        mult_check.setText(resourceMap.getString("mult_check.text")); // NOI18N
        mult_check.setToolTipText(resourceMap.getString("mult_check.toolTipText")); // NOI18N
        mult_check.setActionCommand(resourceMap.getString("mult_check.actionCommand")); // NOI18N
        mult_check.setName("mult_check"); // NOI18N
        mult_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mult_checkmult_info(evt);
            }
        });
        mult_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mult_checkmult_change(evt);
            }
        });
        mult_check.setBounds(20, 10, 110, -1);
        mult_pane.add(mult_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mult_si_check.setBackground(resourceMap.getColor("mult_si_check.background")); // NOI18N
        mult_si_check.setText(resourceMap.getString("mult_si_check.text")); // NOI18N
        mult_si_check.setToolTipText(resourceMap.getString("mult_si_check.toolTipText")); // NOI18N
        mult_si_check.setActionCommand(resourceMap.getString("mult_si_check.actionCommand")); // NOI18N
        mult_si_check.setEnabled(false);
        mult_si_check.setName("mult_si_check"); // NOI18N
        mult_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mult_si_checkmult_info(evt);
            }
        });
        mult_si_check.setBounds(30, 40, 120, -1);
        mult_pane.add(mult_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mult_qi_check.setBackground(resourceMap.getColor("mult_qi_check.background")); // NOI18N
        mult_qi_check.setText(resourceMap.getString("mult_qi_check.text")); // NOI18N
        mult_qi_check.setToolTipText(resourceMap.getString("mult_qi_check.toolTipText")); // NOI18N
        mult_qi_check.setActionCommand(resourceMap.getString("mult_qi_check.actionCommand")); // NOI18N
        mult_qi_check.setEnabled(false);
        mult_qi_check.setName("mult_qi_check"); // NOI18N
        mult_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mult_qi_checkmult_info(evt);
            }
        });
        mult_qi_check.setBounds(30, 60, 120, -1);
        mult_pane.add(mult_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mult_hi_check.setBackground(resourceMap.getColor("mult_hi_check.background")); // NOI18N
        mult_hi_check.setText(resourceMap.getString("mult_hi_check.text")); // NOI18N
        mult_hi_check.setToolTipText(resourceMap.getString("mult_hi_check.toolTipText")); // NOI18N
        mult_hi_check.setActionCommand(resourceMap.getString("mult_hi_check.actionCommand")); // NOI18N
        mult_hi_check.setEnabled(false);
        mult_hi_check.setName("mult_hi_check"); // NOI18N
        mult_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mult_hi_checkmult_info(evt);
            }
        });
        mult_hi_check.setBounds(30, 80, 120, -1);
        mult_pane.add(mult_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mult_pane.setBounds(580, 40, 180, 120);
        arithmetic_insn_tab.add(mult_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        abs_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        abs_pane.setName("abs_pane"); // NOI18N
        abs_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                abs_paneabs_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                abs_panedefault_info(evt);
            }
        });

        abs_check.setBackground(resourceMap.getColor("abs_check.background")); // NOI18N
        abs_check.setText(resourceMap.getString("abs_check.text")); // NOI18N
        abs_check.setToolTipText(resourceMap.getString("abs_check.toolTipText")); // NOI18N
        abs_check.setActionCommand(resourceMap.getString("abs_check.actionCommand")); // NOI18N
        abs_check.setName("abs_check"); // NOI18N
        abs_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                abs_checkabs_info(evt);
            }
        });
        abs_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                abs_checkabs_change(evt);
            }
        });
        abs_check.setBounds(20, 10, 110, -1);
        abs_pane.add(abs_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        abs_si_check.setBackground(resourceMap.getColor("abs_si_check.background")); // NOI18N
        abs_si_check.setText(resourceMap.getString("abs_si_check.text")); // NOI18N
        abs_si_check.setToolTipText(resourceMap.getString("abs_si_check.toolTipText")); // NOI18N
        abs_si_check.setActionCommand(resourceMap.getString("abs_si_check.actionCommand")); // NOI18N
        abs_si_check.setEnabled(false);
        abs_si_check.setName("abs_si_check"); // NOI18N
        abs_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                abs_si_checkabs_info(evt);
            }
        });
        abs_si_check.setBounds(30, 40, 120, -1);
        abs_pane.add(abs_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        abs_qi_check.setBackground(resourceMap.getColor("abs_qi_check.background")); // NOI18N
        abs_qi_check.setText(resourceMap.getString("abs_qi_check.text")); // NOI18N
        abs_qi_check.setToolTipText(resourceMap.getString("abs_qi_check.toolTipText")); // NOI18N
        abs_qi_check.setActionCommand(resourceMap.getString("abs_qi_check.actionCommand")); // NOI18N
        abs_qi_check.setEnabled(false);
        abs_qi_check.setName("abs_qi_check"); // NOI18N
        abs_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                abs_qi_checkabs_info(evt);
            }
        });
        abs_qi_check.setBounds(30, 60, 120, -1);
        abs_pane.add(abs_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        abs_hi_check.setBackground(resourceMap.getColor("abs_hi_check.background")); // NOI18N
        abs_hi_check.setText(resourceMap.getString("abs_hi_check.text")); // NOI18N
        abs_hi_check.setToolTipText(resourceMap.getString("abs_hi_check.toolTipText")); // NOI18N
        abs_hi_check.setActionCommand(resourceMap.getString("abs_hi_check.actionCommand")); // NOI18N
        abs_hi_check.setEnabled(false);
        abs_hi_check.setName("abs_hi_check"); // NOI18N
        abs_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                abs_hi_checkabs_info(evt);
            }
        });
        abs_hi_check.setBounds(30, 80, 120, -1);
        abs_pane.add(abs_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        abs_pane.setBounds(10, 180, 170, 120);
        arithmetic_insn_tab.add(abs_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mod_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mod_pane.setName("mod_pane"); // NOI18N
        mod_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mod_panemod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mod_panedefault_info(evt);
            }
        });

        mod_check.setBackground(resourceMap.getColor("mod_check.background")); // NOI18N
        mod_check.setText(resourceMap.getString("mod_check.text")); // NOI18N
        mod_check.setToolTipText(resourceMap.getString("mod_check.toolTipText")); // NOI18N
        mod_check.setActionCommand(resourceMap.getString("mod_check.actionCommand")); // NOI18N
        mod_check.setName("mod_check"); // NOI18N
        mod_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mod_checkmod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mod_checkdefault_info(evt);
            }
        });
        mod_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mod_checkmod_change(evt);
            }
        });
        mod_check.setBounds(20, 10, 110, -1);
        mod_pane.add(mod_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mod_si_check.setBackground(resourceMap.getColor("mod_si_check.background")); // NOI18N
        mod_si_check.setText(resourceMap.getString("mod_si_check.text")); // NOI18N
        mod_si_check.setToolTipText(resourceMap.getString("mod_si_check.toolTipText")); // NOI18N
        mod_si_check.setActionCommand(resourceMap.getString("mod_si_check.actionCommand")); // NOI18N
        mod_si_check.setEnabled(false);
        mod_si_check.setName("mod_si_check"); // NOI18N
        mod_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mod_si_checkmod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mod_si_checkdefault_info(evt);
            }
        });
        mod_si_check.setBounds(30, 40, 120, -1);
        mod_pane.add(mod_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mod_qi_check.setBackground(resourceMap.getColor("mod_qi_check.background")); // NOI18N
        mod_qi_check.setText(resourceMap.getString("mod_qi_check.text")); // NOI18N
        mod_qi_check.setToolTipText(resourceMap.getString("mod_qi_check.toolTipText")); // NOI18N
        mod_qi_check.setActionCommand(resourceMap.getString("mod_qi_check.actionCommand")); // NOI18N
        mod_qi_check.setEnabled(false);
        mod_qi_check.setName("mod_qi_check"); // NOI18N
        mod_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mod_qi_checkmod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mod_qi_checkdefault_info(evt);
            }
        });
        mod_qi_check.setBounds(30, 60, 120, -1);
        mod_pane.add(mod_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mod_hi_check.setBackground(resourceMap.getColor("mod_hi_check.background")); // NOI18N
        mod_hi_check.setText(resourceMap.getString("mod_hi_check.text")); // NOI18N
        mod_hi_check.setToolTipText(resourceMap.getString("mod_hi_check.toolTipText")); // NOI18N
        mod_hi_check.setActionCommand(resourceMap.getString("mod_hi_check.actionCommand")); // NOI18N
        mod_hi_check.setEnabled(false);
        mod_hi_check.setName("mod_hi_check"); // NOI18N
        mod_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mod_hi_checkmod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mod_hi_checkdefault_info(evt);
            }
        });
        mod_hi_check.setBounds(30, 80, 120, -1);
        mod_pane.add(mod_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mod_pane.setBounds(200, 180, 170, 120);
        arithmetic_insn_tab.add(mod_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        umod_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        umod_pane.setName("umod_pane"); // NOI18N
        umod_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                umod_paneumod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                umod_panedefault_info(evt);
            }
        });

        umod_check.setBackground(resourceMap.getColor("umod_check.background")); // NOI18N
        umod_check.setText(resourceMap.getString("umod_check.text")); // NOI18N
        umod_check.setToolTipText(resourceMap.getString("umod_check.toolTipText")); // NOI18N
        umod_check.setActionCommand(resourceMap.getString("umod_check.actionCommand")); // NOI18N
        umod_check.setName("umod_check"); // NOI18N
        umod_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                umod_checkumod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                umod_checkdefault_info(evt);
            }
        });
        umod_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                umod_checkumod_change(evt);
            }
        });
        umod_check.setBounds(20, 10, 130, -1);
        umod_pane.add(umod_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        umod_si_check.setBackground(resourceMap.getColor("umod_si_check.background")); // NOI18N
        umod_si_check.setText(resourceMap.getString("umod_si_check.text")); // NOI18N
        umod_si_check.setToolTipText(resourceMap.getString("umod_si_check.toolTipText")); // NOI18N
        umod_si_check.setActionCommand(resourceMap.getString("umod_si_check.actionCommand")); // NOI18N
        umod_si_check.setEnabled(false);
        umod_si_check.setName("umod_si_check"); // NOI18N
        umod_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                umod_si_checkumod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                umod_si_checkdefault_info(evt);
            }
        });
        umod_si_check.setBounds(30, 40, 120, -1);
        umod_pane.add(umod_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        umod_qi_check.setBackground(resourceMap.getColor("umod_qi_check.background")); // NOI18N
        umod_qi_check.setText(resourceMap.getString("umod_qi_check.text")); // NOI18N
        umod_qi_check.setToolTipText(resourceMap.getString("umod_qi_check.toolTipText")); // NOI18N
        umod_qi_check.setActionCommand(resourceMap.getString("umod_qi_check.actionCommand")); // NOI18N
        umod_qi_check.setEnabled(false);
        umod_qi_check.setName("umod_qi_check"); // NOI18N
        umod_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                umod_qi_checkumod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                umod_qi_checkdefault_info(evt);
            }
        });
        umod_qi_check.setBounds(30, 60, 120, -1);
        umod_pane.add(umod_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        umod_hi_check.setBackground(resourceMap.getColor("umod_hi_check.background")); // NOI18N
        umod_hi_check.setText(resourceMap.getString("umod_hi_check.text")); // NOI18N
        umod_hi_check.setToolTipText(resourceMap.getString("umod_hi_check.toolTipText")); // NOI18N
        umod_hi_check.setActionCommand(resourceMap.getString("umod_hi_check.actionCommand")); // NOI18N
        umod_hi_check.setEnabled(false);
        umod_hi_check.setName("umod_hi_check"); // NOI18N
        umod_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                umod_hi_checkumod_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                umod_hi_checkdefault_info(evt);
            }
        });
        umod_hi_check.setBounds(30, 80, 120, -1);
        umod_pane.add(umod_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        umod_pane.setBounds(390, 180, 170, 120);
        arithmetic_insn_tab.add(umod_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        arithmetic_separator.setName("arithmetic_separator"); // NOI18N
        arithmetic_separator.setBounds(10, 170, 750, 10);
        arithmetic_insn_tab.add(arithmetic_separator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(md_generator.Md_generatorApp.class).getContext().getActionMap(Md_generatorView.class, this);
        next.setAction(actionMap.get("NextActionPerformed")); // NOI18N
        next.setText(resourceMap.getString("next.text")); // NOI18N
        next.setToolTipText(resourceMap.getString("next.toolTipText")); // NOI18N
        next.setName("next"); // NOI18N
        next.setBounds(690, 280, 73, 23);
        arithmetic_insn_tab.add(next, javax.swing.JLayeredPane.DEFAULT_LAYER);

        insn_type_tab_pane.addTab(resourceMap.getString("arithmetic_insn_tab.TabConstraints.tabTitle"), arithmetic_insn_tab); // NOI18N

        logical_insn_tab.setToolTipText(resourceMap.getString("logical_insn_tab.toolTipText")); // NOI18N
        logical_insn_tab.setEnabled(false);
        logical_insn_tab.setName("logical_insn_tab"); // NOI18N

        logical_label.setFont(resourceMap.getFont("logical_label.font")); // NOI18N
        logical_label.setText(resourceMap.getString("logical_label.text")); // NOI18N
        logical_label.setName("logical_label"); // NOI18N
        logical_label.setBounds(10, 10, 600, -1);
        logical_insn_tab.add(logical_label, javax.swing.JLayeredPane.DEFAULT_LAYER);

        and_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        and_pane.setName("and_pane"); // NOI18N
        and_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                and_paneand_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                and_panedefault_info(evt);
            }
        });

        and_check.setBackground(resourceMap.getColor("and_check.background")); // NOI18N
        and_check.setText(resourceMap.getString("and_check.text")); // NOI18N
        and_check.setToolTipText(resourceMap.getString("and_check.toolTipText")); // NOI18N
        and_check.setActionCommand(resourceMap.getString("and_check.actionCommand")); // NOI18N
        and_check.setName("and_check"); // NOI18N
        and_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                and_checkand_info(evt);
            }
        });
        and_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                and_checkand_change(evt);
            }
        });
        and_check.setBounds(20, 10, 120, -1);
        and_pane.add(and_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        and_si_check.setBackground(resourceMap.getColor("and_si_check.background")); // NOI18N
        and_si_check.setText(resourceMap.getString("and_si_check.text")); // NOI18N
        and_si_check.setToolTipText(resourceMap.getString("and_si_check.toolTipText")); // NOI18N
        and_si_check.setActionCommand(resourceMap.getString("and_si_check.actionCommand")); // NOI18N
        and_si_check.setEnabled(false);
        and_si_check.setName("and_si_check"); // NOI18N
        and_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                and_si_checkand_info(evt);
            }
        });
        and_si_check.setBounds(30, 40, 120, -1);
        and_pane.add(and_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        and_qi_check.setBackground(resourceMap.getColor("and_qi_check.background")); // NOI18N
        and_qi_check.setText(resourceMap.getString("and_qi_check.text")); // NOI18N
        and_qi_check.setToolTipText(resourceMap.getString("and_qi_check.toolTipText")); // NOI18N
        and_qi_check.setActionCommand(resourceMap.getString("and_qi_check.actionCommand")); // NOI18N
        and_qi_check.setEnabled(false);
        and_qi_check.setName("and_qi_check"); // NOI18N
        and_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                and_qi_checkand_info(evt);
            }
        });
        and_qi_check.setBounds(30, 60, -1, -1);
        and_pane.add(and_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        and_hi_check.setBackground(resourceMap.getColor("and_hi_check.background")); // NOI18N
        and_hi_check.setText(resourceMap.getString("and_hi_check.text")); // NOI18N
        and_hi_check.setToolTipText(resourceMap.getString("and_hi_check.toolTipText")); // NOI18N
        and_hi_check.setActionCommand(resourceMap.getString("and_hi_check.actionCommand")); // NOI18N
        and_hi_check.setEnabled(false);
        and_hi_check.setName("and_hi_check"); // NOI18N
        and_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                and_hi_checkand_info(evt);
            }
        });
        and_hi_check.setBounds(30, 80, 110, -1);
        and_pane.add(and_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        and_pane.setBounds(10, 40, 170, 120);
        logical_insn_tab.add(and_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        or_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        or_pane.setName("or_pane"); // NOI18N
        or_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                or_paneor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                or_panedefault_info(evt);
            }
        });

        or_check.setBackground(resourceMap.getColor("or_check.background")); // NOI18N
        or_check.setText(resourceMap.getString("or_check.text")); // NOI18N
        or_check.setToolTipText(resourceMap.getString("or_check.toolTipText")); // NOI18N
        or_check.setActionCommand(resourceMap.getString("or_check.actionCommand")); // NOI18N
        or_check.setName("or_check"); // NOI18N
        or_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                or_checkor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                or_checkdefault_info(evt);
            }
        });
        or_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                or_checkor_change(evt);
            }
        });
        or_check.setBounds(20, 10, 120, -1);
        or_pane.add(or_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        or_si_check.setBackground(resourceMap.getColor("or_si_check.background")); // NOI18N
        or_si_check.setText(resourceMap.getString("or_si_check.text")); // NOI18N
        or_si_check.setToolTipText(resourceMap.getString("or_si_check.toolTipText")); // NOI18N
        or_si_check.setActionCommand(resourceMap.getString("or_si_check.actionCommand")); // NOI18N
        or_si_check.setEnabled(false);
        or_si_check.setName("or_si_check"); // NOI18N
        or_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                or_si_checkor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                or_si_checkdefault_info(evt);
            }
        });
        or_si_check.setBounds(30, 40, 120, -1);
        or_pane.add(or_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        or_qi_check.setBackground(resourceMap.getColor("or_qi_check.background")); // NOI18N
        or_qi_check.setText(resourceMap.getString("or_qi_check.text")); // NOI18N
        or_qi_check.setToolTipText(resourceMap.getString("or_qi_check.toolTipText")); // NOI18N
        or_qi_check.setActionCommand(resourceMap.getString("or_qi_check.actionCommand")); // NOI18N
        or_qi_check.setEnabled(false);
        or_qi_check.setName("or_qi_check"); // NOI18N
        or_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                or_qi_checkor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                or_qi_checkdefault_info(evt);
            }
        });
        or_qi_check.setBounds(30, 60, -1, -1);
        or_pane.add(or_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        or_hi_check.setBackground(resourceMap.getColor("or_hi_check.background")); // NOI18N
        or_hi_check.setText(resourceMap.getString("or_hi_check.text")); // NOI18N
        or_hi_check.setToolTipText(resourceMap.getString("or_hi_check.toolTipText")); // NOI18N
        or_hi_check.setActionCommand(resourceMap.getString("or_hi_check.actionCommand")); // NOI18N
        or_hi_check.setEnabled(false);
        or_hi_check.setName("or_hi_check"); // NOI18N
        or_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                or_hi_checkor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                or_hi_checkdefault_info(evt);
            }
        });
        or_hi_check.setBounds(30, 80, 110, -1);
        or_pane.add(or_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        or_pane.setBounds(200, 40, 170, 120);
        logical_insn_tab.add(or_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        xor_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        xor_pane.setName("xor_pane"); // NOI18N
        xor_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                xor_panexor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                xor_panedefault_info(evt);
            }
        });

        xor_check.setBackground(resourceMap.getColor("xor_check.background")); // NOI18N
        xor_check.setText(resourceMap.getString("xor_check.text")); // NOI18N
        xor_check.setToolTipText(resourceMap.getString("xor_check.toolTipText")); // NOI18N
        xor_check.setActionCommand(resourceMap.getString("xor_check.actionCommand")); // NOI18N
        xor_check.setName("xor_check"); // NOI18N
        xor_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                xor_checkxor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                xor_checkdefault_info(evt);
            }
        });
        xor_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xor_checkxor_change(evt);
            }
        });
        xor_check.setBounds(20, 10, 120, -1);
        xor_pane.add(xor_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        xor_si_check.setBackground(resourceMap.getColor("xor_si_check.background")); // NOI18N
        xor_si_check.setText(resourceMap.getString("xor_si_check.text")); // NOI18N
        xor_si_check.setToolTipText(resourceMap.getString("xor_si_check.toolTipText")); // NOI18N
        xor_si_check.setActionCommand(resourceMap.getString("xor_si_check.actionCommand")); // NOI18N
        xor_si_check.setEnabled(false);
        xor_si_check.setName("xor_si_check"); // NOI18N
        xor_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                xor_si_checkxor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                xor_si_checkdefault_info(evt);
            }
        });
        xor_si_check.setBounds(30, 40, 120, -1);
        xor_pane.add(xor_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        xor_qi_check.setBackground(resourceMap.getColor("xor_qi_check.background")); // NOI18N
        xor_qi_check.setText(resourceMap.getString("xor_qi_check.text")); // NOI18N
        xor_qi_check.setToolTipText(resourceMap.getString("xor_qi_check.toolTipText")); // NOI18N
        xor_qi_check.setActionCommand(resourceMap.getString("xor_qi_check.actionCommand")); // NOI18N
        xor_qi_check.setEnabled(false);
        xor_qi_check.setName("xor_qi_check"); // NOI18N
        xor_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                xor_qi_checkxor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                xor_qi_checkdefault_info(evt);
            }
        });
        xor_qi_check.setBounds(30, 60, -1, -1);
        xor_pane.add(xor_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        xor_hi_check.setBackground(resourceMap.getColor("xor_hi_check.background")); // NOI18N
        xor_hi_check.setText(resourceMap.getString("xor_hi_check.text")); // NOI18N
        xor_hi_check.setToolTipText(resourceMap.getString("xor_hi_check.toolTipText")); // NOI18N
        xor_hi_check.setActionCommand(resourceMap.getString("xor_hi_check.actionCommand")); // NOI18N
        xor_hi_check.setEnabled(false);
        xor_hi_check.setName("xor_hi_check"); // NOI18N
        xor_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                xor_hi_checkxor_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                xor_hi_checkdefault_info(evt);
            }
        });
        xor_hi_check.setBounds(30, 80, 110, -1);
        xor_pane.add(xor_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        xor_pane.setBounds(390, 40, 170, 120);
        logical_insn_tab.add(xor_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashl_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ashl_pane.setName("ashl_pane"); // NOI18N
        ashl_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashl_paneashl_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ashl_panedefault_info(evt);
            }
        });

        ashl_check.setBackground(resourceMap.getColor("ashl_check.background")); // NOI18N
        ashl_check.setText(resourceMap.getString("ashl_check.text")); // NOI18N
        ashl_check.setToolTipText(resourceMap.getString("ashl_check.toolTipText")); // NOI18N
        ashl_check.setActionCommand(resourceMap.getString("ashl_check.actionCommand")); // NOI18N
        ashl_check.setName("ashl_check"); // NOI18N
        ashl_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashl_checkashl_info(evt);
            }
        });
        ashl_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ashl_checkashl_change(evt);
            }
        });
        ashl_check.setBounds(20, 10, 130, -1);
        ashl_pane.add(ashl_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashl_si_check.setBackground(resourceMap.getColor("ashl_si_check.background")); // NOI18N
        ashl_si_check.setText(resourceMap.getString("ashl_si_check.text")); // NOI18N
        ashl_si_check.setToolTipText(resourceMap.getString("ashl_si_check.toolTipText")); // NOI18N
        ashl_si_check.setActionCommand(resourceMap.getString("ashl_si_check.actionCommand")); // NOI18N
        ashl_si_check.setEnabled(false);
        ashl_si_check.setName("ashl_si_check"); // NOI18N
        ashl_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashl_si_checkashl_info(evt);
            }
        });
        ashl_si_check.setBounds(30, 40, 120, -1);
        ashl_pane.add(ashl_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashl_qi_check.setBackground(resourceMap.getColor("ashl_qi_check.background")); // NOI18N
        ashl_qi_check.setText(resourceMap.getString("ashl_qi_check.text")); // NOI18N
        ashl_qi_check.setToolTipText(resourceMap.getString("ashl_qi_check.toolTipText")); // NOI18N
        ashl_qi_check.setActionCommand(resourceMap.getString("ashl_qi_check.actionCommand")); // NOI18N
        ashl_qi_check.setEnabled(false);
        ashl_qi_check.setName("ashl_qi_check"); // NOI18N
        ashl_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashl_qi_checkashl_info(evt);
            }
        });
        ashl_qi_check.setBounds(30, 60, -1, -1);
        ashl_pane.add(ashl_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashl_hi_check.setBackground(resourceMap.getColor("ashl_hi_check.background")); // NOI18N
        ashl_hi_check.setText(resourceMap.getString("ashl_hi_check.text")); // NOI18N
        ashl_hi_check.setToolTipText(resourceMap.getString("ashl_hi_check.toolTipText")); // NOI18N
        ashl_hi_check.setActionCommand(resourceMap.getString("ashl_hi_check.actionCommand")); // NOI18N
        ashl_hi_check.setEnabled(false);
        ashl_hi_check.setName("ashl_hi_check"); // NOI18N
        ashl_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashl_hi_checkashl_info(evt);
            }
        });
        ashl_hi_check.setBounds(30, 80, 110, -1);
        ashl_pane.add(ashl_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashl_pane.setBounds(10, 180, 170, 120);
        logical_insn_tab.add(ashl_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashr_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ashr_pane.setName("ashr_pane"); // NOI18N
        ashr_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashr_paneashr_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ashr_panedefault_info(evt);
            }
        });

        ashr_check.setBackground(resourceMap.getColor("ashr_check.background")); // NOI18N
        ashr_check.setText(resourceMap.getString("ashr_check.text")); // NOI18N
        ashr_check.setToolTipText(resourceMap.getString("ashr_check.toolTipText")); // NOI18N
        ashr_check.setActionCommand(resourceMap.getString("ashr_check.actionCommand")); // NOI18N
        ashr_check.setName("ashr_check"); // NOI18N
        ashr_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashr_checkashr_info(evt);
            }
        });
        ashr_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ashr_checkashr_change(evt);
            }
        });
        ashr_check.setBounds(20, 10, 130, -1);
        ashr_pane.add(ashr_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashr_si_check.setBackground(resourceMap.getColor("ashr_si_check.background")); // NOI18N
        ashr_si_check.setText(resourceMap.getString("ashr_si_check.text")); // NOI18N
        ashr_si_check.setToolTipText(resourceMap.getString("ashr_si_check.toolTipText")); // NOI18N
        ashr_si_check.setActionCommand(resourceMap.getString("ashr_si_check.actionCommand")); // NOI18N
        ashr_si_check.setEnabled(false);
        ashr_si_check.setName("ashr_si_check"); // NOI18N
        ashr_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashr_si_checkashr_info(evt);
            }
        });
        ashr_si_check.setBounds(30, 40, 120, -1);
        ashr_pane.add(ashr_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashr_qi_check.setBackground(resourceMap.getColor("ashr_qi_check.background")); // NOI18N
        ashr_qi_check.setText(resourceMap.getString("ashr_qi_check.text")); // NOI18N
        ashr_qi_check.setToolTipText(resourceMap.getString("ashr_qi_check.toolTipText")); // NOI18N
        ashr_qi_check.setActionCommand(resourceMap.getString("ashr_qi_check.actionCommand")); // NOI18N
        ashr_qi_check.setEnabled(false);
        ashr_qi_check.setName("ashr_qi_check"); // NOI18N
        ashr_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashr_qi_checkashr_info(evt);
            }
        });
        ashr_qi_check.setBounds(30, 60, -1, -1);
        ashr_pane.add(ashr_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashr_hi_check.setBackground(resourceMap.getColor("ashr_hi_check.background")); // NOI18N
        ashr_hi_check.setText(resourceMap.getString("ashr_hi_check.text")); // NOI18N
        ashr_hi_check.setToolTipText(resourceMap.getString("ashr_hi_check.toolTipText")); // NOI18N
        ashr_hi_check.setActionCommand(resourceMap.getString("ashr_hi_check.actionCommand")); // NOI18N
        ashr_hi_check.setEnabled(false);
        ashr_hi_check.setName("ashr_hi_check"); // NOI18N
        ashr_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ashr_hi_checkashr_info(evt);
            }
        });
        ashr_hi_check.setBounds(30, 80, 110, -1);
        ashr_pane.add(ashr_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ashr_pane.setBounds(200, 180, 170, 120);
        logical_insn_tab.add(ashr_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        logical_separator.setName("logical_separator"); // NOI18N
        logical_separator.setBounds(10, 170, 750, 10);
        logical_insn_tab.add(logical_separator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        next1.setAction(actionMap.get("NextActionPerformed")); // NOI18N
        next1.setText(resourceMap.getString("next1.text")); // NOI18N
        next1.setToolTipText(resourceMap.getString("next1.toolTipText")); // NOI18N
        next1.setName("next1"); // NOI18N
        next1.setBounds(690, 280, 73, 23);
        logical_insn_tab.add(next1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        back.setAction(actionMap.get("BackActionPerformed")); // NOI18N
        back.setText(resourceMap.getString("back.text")); // NOI18N
        back.setToolTipText(resourceMap.getString("back.toolTipText")); // NOI18N
        back.setName("back"); // NOI18N
        back.setBounds(610, 280, -1, -1);
        logical_insn_tab.add(back, javax.swing.JLayeredPane.DEFAULT_LAYER);

        insn_type_tab_pane.addTab(resourceMap.getString("logical_insn_tab.TabConstraints.tabTitle"), logical_insn_tab); // NOI18N

        data_transfer_insn_tab.setToolTipText(resourceMap.getString("data_transfer_insn_tab.toolTipText")); // NOI18N
        data_transfer_insn_tab.setName("data_transfer_insn_tab"); // NOI18N

        mov_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mov_pane.setName("mov_pane"); // NOI18N
        mov_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mov_panemov_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mov_panedefault_info(evt);
            }
        });

        mov_check.setBackground(resourceMap.getColor("mov_check.background")); // NOI18N
        mov_check.setText(resourceMap.getString("mov_check.text")); // NOI18N
        mov_check.setToolTipText(resourceMap.getString("mov_check.toolTipText")); // NOI18N
        mov_check.setActionCommand(resourceMap.getString("mov_check.actionCommand")); // NOI18N
        mov_check.setName("mov_check"); // NOI18N
        mov_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mov_checkmov_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mov_checkdefault_info(evt);
            }
        });
        mov_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mov_checkmov_change(evt);
            }
        });
        mov_check.setBounds(20, 10, 110, -1);
        mov_pane.add(mov_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mov_si_check.setBackground(resourceMap.getColor("mov_si_check.background")); // NOI18N
        mov_si_check.setText(resourceMap.getString("mov_si_check.text")); // NOI18N
        mov_si_check.setToolTipText(resourceMap.getString("mov_si_check.toolTipText")); // NOI18N
        mov_si_check.setActionCommand(resourceMap.getString("mov_si_check.actionCommand")); // NOI18N
        mov_si_check.setEnabled(false);
        mov_si_check.setName("mov_si_check"); // NOI18N
        mov_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mov_si_checkmov_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mov_si_checkdefault_info(evt);
            }
        });
        mov_si_check.setBounds(30, 40, 120, -1);
        mov_pane.add(mov_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mov_qi_check.setBackground(resourceMap.getColor("mov_qi_check.background")); // NOI18N
        mov_qi_check.setText(resourceMap.getString("mov_qi_check.text")); // NOI18N
        mov_qi_check.setToolTipText(resourceMap.getString("mov_qi_check.toolTipText")); // NOI18N
        mov_qi_check.setActionCommand(resourceMap.getString("mov_qi_check.actionCommand")); // NOI18N
        mov_qi_check.setEnabled(false);
        mov_qi_check.setName("mov_qi_check"); // NOI18N
        mov_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mov_qi_checkmov_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mov_qi_checkdefault_info(evt);
            }
        });
        mov_qi_check.setBounds(30, 60, 120, -1);
        mov_pane.add(mov_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mov_hi_check.setBackground(resourceMap.getColor("mov_hi_check.background")); // NOI18N
        mov_hi_check.setText(resourceMap.getString("mov_hi_check.text")); // NOI18N
        mov_hi_check.setToolTipText(resourceMap.getString("mov_hi_check.toolTipText")); // NOI18N
        mov_hi_check.setActionCommand(resourceMap.getString("mov_hi_check.actionCommand")); // NOI18N
        mov_hi_check.setEnabled(false);
        mov_hi_check.setName("mov_hi_check"); // NOI18N
        mov_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mov_hi_checkmov_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mov_hi_checkdefault_info(evt);
            }
        });
        mov_hi_check.setBounds(30, 80, 120, -1);
        mov_pane.add(mov_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mov_pane.setBounds(10, 40, 170, 120);
        data_transfer_insn_tab.add(mov_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        data_label.setFont(resourceMap.getFont("data_label.font")); // NOI18N
        data_label.setText(resourceMap.getString("data_label.text")); // NOI18N
        data_label.setName("data_label"); // NOI18N
        data_label.setBounds(10, 10, 600, -1);
        data_transfer_insn_tab.add(data_label, javax.swing.JLayeredPane.DEFAULT_LAYER);

        load_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        load_pane.setName("load_pane"); // NOI18N
        load_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                load_paneload_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                load_panedefault_info(evt);
            }
        });

        load_check.setBackground(resourceMap.getColor("load_check.background")); // NOI18N
        load_check.setText(resourceMap.getString("load_check.text")); // NOI18N
        load_check.setToolTipText(resourceMap.getString("load_check.toolTipText")); // NOI18N
        load_check.setActionCommand(resourceMap.getString("load_check.actionCommand")); // NOI18N
        load_check.setName("load_check"); // NOI18N
        load_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                load_checkload_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                load_checkdefault_info(evt);
            }
        });
        load_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                load_checkload_change(evt);
            }
        });
        load_check.setBounds(20, 10, 110, -1);
        load_pane.add(load_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        load_si_check.setBackground(resourceMap.getColor("load_si_check.background")); // NOI18N
        load_si_check.setText(resourceMap.getString("load_si_check.text")); // NOI18N
        load_si_check.setToolTipText(resourceMap.getString("load_si_check.toolTipText")); // NOI18N
        load_si_check.setActionCommand(resourceMap.getString("load_si_check.actionCommand")); // NOI18N
        load_si_check.setEnabled(false);
        load_si_check.setName("load_si_check"); // NOI18N
        load_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                load_si_checkload_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                load_si_checkdefault_info(evt);
            }
        });
        load_si_check.setBounds(30, 40, 120, -1);
        load_pane.add(load_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        load_qi_check.setBackground(resourceMap.getColor("load_qi_check.background")); // NOI18N
        load_qi_check.setText(resourceMap.getString("load_qi_check.text")); // NOI18N
        load_qi_check.setToolTipText(resourceMap.getString("load_qi_check.toolTipText")); // NOI18N
        load_qi_check.setActionCommand(resourceMap.getString("load_qi_check.actionCommand")); // NOI18N
        load_qi_check.setEnabled(false);
        load_qi_check.setName("load_qi_check"); // NOI18N
        load_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                load_qi_checkload_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                load_qi_checkdefault_info(evt);
            }
        });
        load_qi_check.setBounds(30, 60, 120, -1);
        load_pane.add(load_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        load_hi_check.setBackground(resourceMap.getColor("load_hi_check.background")); // NOI18N
        load_hi_check.setText(resourceMap.getString("load_hi_check.text")); // NOI18N
        load_hi_check.setToolTipText(resourceMap.getString("load_hi_check.toolTipText")); // NOI18N
        load_hi_check.setActionCommand(resourceMap.getString("load_hi_check.actionCommand")); // NOI18N
        load_hi_check.setEnabled(false);
        load_hi_check.setName("load_hi_check"); // NOI18N
        load_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                load_hi_checkload_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                load_hi_checkdefault_info(evt);
            }
        });
        load_hi_check.setBounds(30, 80, 120, -1);
        load_pane.add(load_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        load_pane.setBounds(200, 40, 170, 120);
        data_transfer_insn_tab.add(load_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        store_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        store_pane.setName("store_pane"); // NOI18N
        store_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                store_panestore_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                store_panedefault_info(evt);
            }
        });

        store_check.setBackground(resourceMap.getColor("store_check.background")); // NOI18N
        store_check.setText(resourceMap.getString("store_check.text")); // NOI18N
        store_check.setToolTipText(resourceMap.getString("store_check.toolTipText")); // NOI18N
        store_check.setActionCommand(resourceMap.getString("store_check.actionCommand")); // NOI18N
        store_check.setName("store_check"); // NOI18N
        store_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                store_checkstore_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                store_checkdefault_info(evt);
            }
        });
        store_check.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                store_checkstore_change(evt);
            }
        });
        store_check.setBounds(20, 10, 110, -1);
        store_pane.add(store_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        store_si_check.setBackground(resourceMap.getColor("store_si_check.background")); // NOI18N
        store_si_check.setText(resourceMap.getString("store_si_check.text")); // NOI18N
        store_si_check.setToolTipText(resourceMap.getString("store_si_check.toolTipText")); // NOI18N
        store_si_check.setActionCommand(resourceMap.getString("store_si_check.actionCommand")); // NOI18N
        store_si_check.setEnabled(false);
        store_si_check.setName("store_si_check"); // NOI18N
        store_si_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                store_si_checkstore_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                store_si_checkdefault_info(evt);
            }
        });
        store_si_check.setBounds(30, 40, 120, -1);
        store_pane.add(store_si_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        store_qi_check.setBackground(resourceMap.getColor("store_qi_check.background")); // NOI18N
        store_qi_check.setText(resourceMap.getString("store_qi_check.text")); // NOI18N
        store_qi_check.setToolTipText(resourceMap.getString("store_qi_check.toolTipText")); // NOI18N
        store_qi_check.setActionCommand(resourceMap.getString("store_qi_check.actionCommand")); // NOI18N
        store_qi_check.setEnabled(false);
        store_qi_check.setName("store_qi_check"); // NOI18N
        store_qi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                store_qi_checkstore_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                store_qi_checkdefault_info(evt);
            }
        });
        store_qi_check.setBounds(30, 60, 120, -1);
        store_pane.add(store_qi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        store_hi_check.setBackground(resourceMap.getColor("store_hi_check.background")); // NOI18N
        store_hi_check.setText(resourceMap.getString("store_hi_check.text")); // NOI18N
        store_hi_check.setToolTipText(resourceMap.getString("store_hi_check.toolTipText")); // NOI18N
        store_hi_check.setActionCommand(resourceMap.getString("store_hi_check.actionCommand")); // NOI18N
        store_hi_check.setEnabled(false);
        store_hi_check.setName("store_hi_check"); // NOI18N
        store_hi_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                store_hi_checkstore_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                store_hi_checkdefault_info(evt);
            }
        });
        store_hi_check.setBounds(30, 80, 120, -1);
        store_pane.add(store_hi_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        store_pane.setBounds(390, 40, 170, 120);
        data_transfer_insn_tab.add(store_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        data_transfer_separator.setName("data_transfer_separator"); // NOI18N
        data_transfer_separator.setBounds(10, 170, 750, 10);
        data_transfer_insn_tab.add(data_transfer_separator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        next2.setAction(actionMap.get("NextActionPerformed")); // NOI18N
        next2.setText(resourceMap.getString("next2.text")); // NOI18N
        next2.setToolTipText(resourceMap.getString("next2.toolTipText")); // NOI18N
        next2.setName("next2"); // NOI18N
        next2.setBounds(690, 280, 73, 23);
        data_transfer_insn_tab.add(next2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        back2.setAction(actionMap.get("BackActionPerformed")); // NOI18N
        back2.setText(resourceMap.getString("back2.text")); // NOI18N
        back2.setToolTipText(resourceMap.getString("back2.toolTipText")); // NOI18N
        back2.setName("back2"); // NOI18N
        back2.setBounds(610, 280, -1, -1);
        data_transfer_insn_tab.add(back2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        insn_type_tab_pane.addTab(resourceMap.getString("data_transfer_insn_tab.TabConstraints.tabTitle"), data_transfer_insn_tab); // NOI18N

        jump_insn_tab.setToolTipText(resourceMap.getString("jump_insn_tab.toolTipText")); // NOI18N
        jump_insn_tab.setName("jump_insn_tab"); // NOI18N

        jump_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jump_pane.setName("jump_pane"); // NOI18N
        jump_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jump_panejump_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jump_panedefault_info(evt);
            }
        });

        jump_check.setBackground(resourceMap.getColor("jump_check.background")); // NOI18N
        jump_check.setSelected(true);
        jump_check.setText(resourceMap.getString("jump_check.text")); // NOI18N
        jump_check.setToolTipText(resourceMap.getString("jump_check.toolTipText")); // NOI18N
        jump_check.setActionCommand(resourceMap.getString("jump_check.actionCommand")); // NOI18N
        jump_check.setEnabled(false);
        jump_check.setName("jump_check"); // NOI18N
        jump_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jump_checkjump_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jump_checkdefault_info(evt);
            }
        });
        jump_check.setBounds(20, 10, 110, -1);
        jump_pane.add(jump_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jump_pane.setBounds(10, 40, 160, 50);
        jump_insn_tab.add(jump_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ijump_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ijump_pane.setName("ijump_pane"); // NOI18N
        ijump_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ijump_paneijump_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ijump_panedefault_info(evt);
            }
        });

        ijump_check.setBackground(resourceMap.getColor("ijump_check.background")); // NOI18N
        ijump_check.setSelected(true);
        ijump_check.setText(resourceMap.getString("ijump_check.text")); // NOI18N
        ijump_check.setToolTipText(resourceMap.getString("ijump_check.toolTipText")); // NOI18N
        ijump_check.setActionCommand(resourceMap.getString("ijump_check.actionCommand")); // NOI18N
        ijump_check.setEnabled(false);
        ijump_check.setName("ijump_check"); // NOI18N
        ijump_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ijump_checkijump_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ijump_checkdefault_info(evt);
            }
        });
        ijump_check.setBounds(20, 10, 130, -1);
        ijump_pane.add(ijump_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ijump_pane.setBounds(190, 40, 160, 50);
        jump_insn_tab.add(ijump_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        epilogue_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        epilogue_pane.setName("epilogue_pane"); // NOI18N
        epilogue_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                epilogue_paneepi_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                epilogue_panedefault_info(evt);
            }
        });

        epilogue_check.setBackground(resourceMap.getColor("epilogue_check.background")); // NOI18N
        epilogue_check.setText(resourceMap.getString("epilogue_check.text")); // NOI18N
        epilogue_check.setToolTipText(resourceMap.getString("epilogue_check.toolTipText")); // NOI18N
        epilogue_check.setActionCommand(resourceMap.getString("epilogue_check.actionCommand")); // NOI18N
        epilogue_check.setName("epilogue_check"); // NOI18N
        epilogue_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                epilogue_checkepi_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                epilogue_checkdefault_info(evt);
            }
        });
        epilogue_check.setBounds(20, 10, 130, -1);
        epilogue_pane.add(epilogue_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        epilogue_pane.setBounds(10, 110, 160, 50);
        jump_insn_tab.add(epilogue_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        prologue_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        prologue_pane.setName("prologue_pane"); // NOI18N
        prologue_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                prologue_panepro_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                prologue_panedefault_info(evt);
            }
        });

        prologue_check.setBackground(resourceMap.getColor("prologue_check.background")); // NOI18N
        prologue_check.setText(resourceMap.getString("prologue_check.text")); // NOI18N
        prologue_check.setToolTipText(resourceMap.getString("prologue_check.toolTipText")); // NOI18N
        prologue_check.setActionCommand(resourceMap.getString("prologue_check.actionCommand")); // NOI18N
        prologue_check.setName("prologue_check"); // NOI18N
        prologue_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                prologue_checkpro_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                prologue_checkdefault_info(evt);
            }
        });
        prologue_check.setBounds(20, 10, -1, -1);
        prologue_pane.add(prologue_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        prologue_pane.setBounds(190, 110, 160, 50);
        jump_insn_tab.add(prologue_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jump_label.setFont(resourceMap.getFont("jump_label.font")); // NOI18N
        jump_label.setText(resourceMap.getString("jump_label.text")); // NOI18N
        jump_label.setName("jump_label"); // NOI18N
        jump_label.setBounds(10, 10, 600, -1);
        jump_insn_tab.add(jump_label, javax.swing.JLayeredPane.DEFAULT_LAYER);

        call_pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        call_pane.setName("call_pane"); // NOI18N
        call_pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                call_panecall_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                call_panedefault_info(evt);
            }
        });

        call_check.setBackground(resourceMap.getColor("call_check.background")); // NOI18N
        call_check.setText(resourceMap.getString("call_check.text")); // NOI18N
        call_check.setToolTipText(resourceMap.getString("call_check.toolTipText")); // NOI18N
        call_check.setActionCommand(resourceMap.getString("call_check.actionCommand")); // NOI18N
        call_check.setName("call_check"); // NOI18N
        call_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                call_checkcall_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                call_checkdefault_info(evt);
            }
        });
        call_check.setBounds(20, 10, 110, -1);
        call_pane.add(call_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        call_pane.setBounds(370, 110, 160, 50);
        jump_insn_tab.add(call_pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        call_value__pane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        call_value__pane.setName("call_value__pane"); // NOI18N
        call_value__pane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                call_value__panecall_value_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                call_value__panedefault_info(evt);
            }
        });

        call_value_check.setBackground(resourceMap.getColor("call_value_check.background")); // NOI18N
        call_value_check.setText(resourceMap.getString("call_value_check.text")); // NOI18N
        call_value_check.setToolTipText(resourceMap.getString("call_value_check.toolTipText")); // NOI18N
        call_value_check.setActionCommand(resourceMap.getString("call_value_check.actionCommand")); // NOI18N
        call_value_check.setName("call_value_check"); // NOI18N
        call_value_check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                call_value_checkcall_value_info(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                call_value_checkdefault_info(evt);
            }
        });
        call_value_check.setBounds(20, 10, 130, -1);
        call_value__pane.add(call_value_check, javax.swing.JLayeredPane.DEFAULT_LAYER);

        call_value__pane.setBounds(550, 110, 160, 50);
        jump_insn_tab.add(call_value__pane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jump_separator.setName("jump_separator"); // NOI18N
        jump_separator.setBounds(10, 100, 750, 10);
        jump_insn_tab.add(jump_separator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        back3.setAction(actionMap.get("BackActionPerformed")); // NOI18N
        back3.setText(resourceMap.getString("back3.text")); // NOI18N
        back3.setToolTipText(resourceMap.getString("back3.toolTipText")); // NOI18N
        back3.setName("back3"); // NOI18N
        back3.setBounds(610, 280, -1, -1);
        jump_insn_tab.add(back3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        next3.setAction(actionMap.get("NextActionPerformed")); // NOI18N
        next3.setText(resourceMap.getString("next3.text")); // NOI18N
        next3.setToolTipText(resourceMap.getString("next3.toolTipText")); // NOI18N
        next3.setName("next3"); // NOI18N
        next3.setBounds(690, 280, 73, 23);
        jump_insn_tab.add(next3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        insn_type_tab_pane.addTab(resourceMap.getString("jump_insn_tab.TabConstraints.tabTitle"), jump_insn_tab); // NOI18N

        jLayeredPane2.setName("jLayeredPane2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setBounds(20, 30, -1, -1);
        jLayeredPane2.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox1.setSelectedIndex(1);
        jComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox1.setName("jComboBox1"); // NOI18N
        jComboBox1.setBounds(80, 30, -1, -1);
        jLayeredPane2.add(jComboBox1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setBounds(210, 30, -1, -1);
        jLayeredPane2.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox2.setSelectedIndex(1);
        jComboBox2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox2.setName("jComboBox2"); // NOI18N
        jComboBox2.setBounds(270, 30, -1, -1);
        jLayeredPane2.add(jComboBox2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setBounds(410, 30, -1, -1);
        jLayeredPane2.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox3.setSelectedIndex(1);
        jComboBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox3.setName("jComboBox3"); // NOI18N
        jComboBox3.setBounds(470, 30, -1, -1);
        jLayeredPane2.add(jComboBox3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        jLabel4.setBounds(610, 30, -1, -1);
        jLayeredPane2.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox4.setSelectedIndex(1);
        jComboBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox4.setName("jComboBox4"); // NOI18N
        jComboBox4.setBounds(670, 30, -1, -1);
        jLayeredPane2.add(jComboBox4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setBounds(20, 70, -1, -1);
        jLayeredPane2.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox5.setSelectedIndex(1);
        jComboBox5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox5.setName("jComboBox5"); // NOI18N
        jComboBox5.setBounds(80, 70, -1, -1);
        jLayeredPane2.add(jComboBox5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setBounds(210, 70, -1, -1);
        jLayeredPane2.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox6.setSelectedIndex(1);
        jComboBox6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox6.setName("jComboBox6"); // NOI18N
        jComboBox6.setBounds(270, 70, -1, -1);
        jLayeredPane2.add(jComboBox6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setBounds(410, 70, -1, -1);
        jLayeredPane2.add(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox7.setSelectedIndex(1);
        jComboBox7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox7.setName("jComboBox7"); // NOI18N
        jComboBox7.setBounds(470, 70, -1, -1);
        jLayeredPane2.add(jComboBox7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setBounds(610, 70, -1, -1);
        jLayeredPane2.add(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox8.setSelectedIndex(1);
        jComboBox8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox8.setName("jComboBox8"); // NOI18N
        jComboBox8.setBounds(670, 70, -1, -1);
        jLayeredPane2.add(jComboBox8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setBounds(20, 110, -1, -1);
        jLayeredPane2.add(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox9.setSelectedIndex(1);
        jComboBox9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox9.setName("jComboBox9"); // NOI18N
        jComboBox9.setBounds(80, 110, -1, -1);
        jLayeredPane2.add(jComboBox9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setBounds(210, 110, -1, -1);
        jLayeredPane2.add(jLabel20, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox10.setSelectedIndex(1);
        jComboBox10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox10.setName("jComboBox10"); // NOI18N
        jComboBox10.setBounds(270, 110, -1, -1);
        jLayeredPane2.add(jComboBox10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setBounds(410, 110, -1, -1);
        jLayeredPane2.add(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox11.setSelectedIndex(1);
        jComboBox11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox11.setName("jComboBox11"); // NOI18N
        jComboBox11.setBounds(470, 110, -1, -1);
        jLayeredPane2.add(jComboBox11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setBounds(610, 110, -1, -1);
        jLayeredPane2.add(jLabel12, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox12.setSelectedIndex(1);
        jComboBox12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox12.setName("jComboBox12"); // NOI18N
        jComboBox12.setBounds(670, 110, -1, -1);
        jLayeredPane2.add(jComboBox12, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setBounds(20, 150, -1, -1);
        jLayeredPane2.add(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox13.setSelectedIndex(1);
        jComboBox13.setName("jComboBox13"); // NOI18N
        jComboBox13.setBounds(80, 150, -1, -1);
        jLayeredPane2.add(jComboBox13, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setBounds(210, 150, -1, -1);
        jLayeredPane2.add(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox14.setSelectedIndex(1);
        jComboBox14.setName("jComboBox14"); // NOI18N
        jComboBox14.setBounds(270, 150, -1, -1);
        jLayeredPane2.add(jComboBox14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setBounds(410, 150, -1, -1);
        jLayeredPane2.add(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox15.setSelectedIndex(2);
        jComboBox15.setName("jComboBox15"); // NOI18N
        jComboBox15.setBounds(470, 150, -1, -1);
        jLayeredPane2.add(jComboBox15, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setBounds(610, 150, -1, -1);
        jLayeredPane2.add(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox16.setSelectedIndex(2);
        jComboBox16.setName("jComboBox16"); // NOI18N
        jComboBox16.setBounds(670, 150, -1, -1);
        jLayeredPane2.add(jComboBox16, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setBounds(20, 190, -1, -1);
        jLayeredPane2.add(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox17.setSelectedIndex(2);
        jComboBox17.setName("jComboBox17"); // NOI18N
        jComboBox17.setBounds(80, 190, -1, -1);
        jLayeredPane2.add(jComboBox17, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setBounds(210, 190, -1, -1);
        jLayeredPane2.add(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox18.setSelectedIndex(2);
        jComboBox18.setName("jComboBox18"); // NOI18N
        jComboBox18.setBounds(270, 190, -1, -1);
        jLayeredPane2.add(jComboBox18, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setBounds(410, 190, -1, -1);
        jLayeredPane2.add(jLabel19, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox19.setSelectedIndex(2);
        jComboBox19.setName("jComboBox19"); // NOI18N
        jComboBox19.setBounds(470, 190, -1, -1);
        jLayeredPane2.add(jComboBox19, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setBounds(610, 190, -1, -1);
        jLayeredPane2.add(jLabel18, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox20.setSelectedIndex(2);
        jComboBox20.setName("jComboBox20"); // NOI18N
        jComboBox20.setBounds(670, 190, -1, -1);
        jLayeredPane2.add(jComboBox20, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setBounds(20, 230, -1, -1);
        jLayeredPane2.add(jLabel21, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox21.setSelectedIndex(2);
        jComboBox21.setName("jComboBox21"); // NOI18N
        jComboBox21.setBounds(80, 230, -1, -1);
        jLayeredPane2.add(jComboBox21, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel23.setFont(resourceMap.getFont("jLabel23.font")); // NOI18N
        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setBounds(320, 0, -1, -1);
        jLayeredPane2.add(jLabel23, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setBounds(210, 230, -1, -1);
        jLayeredPane2.add(jLabel22, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox22.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox22.setSelectedIndex(2);
        jComboBox22.setName("jComboBox22"); // NOI18N
        jComboBox22.setBounds(270, 230, -1, -1);
        jLayeredPane2.add(jComboBox22, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setBounds(410, 230, -1, -1);
        jLayeredPane2.add(jLabel24, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox23.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox23.setSelectedIndex(1);
        jComboBox23.setName("jComboBox23"); // NOI18N
        jComboBox23.setBounds(470, 230, -1, -1);
        jLayeredPane2.add(jComboBox23, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setBounds(610, 230, -1, -1);
        jLayeredPane2.add(jLabel25, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jComboBox24.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fixed Registers", "Caller reg", "Callee reg" }));
        jComboBox24.setSelectedIndex(1);
        jComboBox24.setName("jComboBox24"); // NOI18N
        jComboBox24.setBounds(670, 230, -1, -1);
        jLayeredPane2.add(jComboBox24, javax.swing.JLayeredPane.DEFAULT_LAYER);

        back4.setAction(actionMap.get("BackActionPerformed")); // NOI18N
        back4.setText(resourceMap.getString("back4.text")); // NOI18N
        back4.setToolTipText(resourceMap.getString("back4.toolTipText")); // NOI18N
        back4.setName("back4"); // NOI18N
        back4.setBounds(610, 280, -1, -1);
        jLayeredPane2.add(back4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        insn_type_tab_pane.addTab(resourceMap.getString("jLayeredPane2.TabConstraints.tabTitle"), jLayeredPane2); // NOI18N

        info.setName("info"); // NOI18N

        info_text_area.setColumns(23);
        info_text_area.setEditable(false);
        info_text_area.setLineWrap(true);
        info_text_area.setRows(6);
        info_text_area.setText(resourceMap.getString("info_text_area.text")); // NOI18N
        info_text_area.setWrapStyleWord(true);
        info_text_area.setName("info_text_area"); // NOI18N
        info.setViewportView(info_text_area);

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLayeredPane1.setName("jLayeredPane1"); // NOI18N

        select_all_si.setAction(actionMap.get("select_all_si")); // NOI18N
        select_all_si.setText(resourceMap.getString("select_all_si.text")); // NOI18N
        select_all_si.setName("select_all_si"); // NOI18N
        select_all_si.setBounds(20, 10, 130, -1);
        jLayeredPane1.add(select_all_si, javax.swing.JLayeredPane.DEFAULT_LAYER);

        select_all_qi.setAction(actionMap.get("select_all_qi")); // NOI18N
        select_all_qi.setText(resourceMap.getString("select_all_qi.text")); // NOI18N
        select_all_qi.setName("select_all_qi"); // NOI18N
        select_all_qi.setBounds(20, 40, 130, -1);
        jLayeredPane1.add(select_all_qi, javax.swing.JLayeredPane.DEFAULT_LAYER);

        select_all_hi.setAction(actionMap.get("select_all_hi")); // NOI18N
        select_all_hi.setText(resourceMap.getString("select_all_hi.text")); // NOI18N
        select_all_hi.setName("select_all_hi"); // NOI18N
        select_all_hi.setBounds(20, 70, 130, -1);
        jLayeredPane1.add(select_all_hi, javax.swing.JLayeredPane.DEFAULT_LAYER);

        clear_all.setAction(actionMap.get("clear_all")); // NOI18N
        clear_all.setText(resourceMap.getString("clear_all.text")); // NOI18N
        clear_all.setName("clear_all"); // NOI18N
        clear_all.setBounds(20, 100, 130, -1);
        jLayeredPane1.add(clear_all, javax.swing.JLayeredPane.DEFAULT_LAYER);

        gen_button_pane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gen_button_pane1.setName("gen_button_pane1"); // NOI18N

        gen_c.setAction(actionMap.get("copy_c_file")); // NOI18N
        gen_c.setText(resourceMap.getString("gen_c.text")); // NOI18N
        gen_c.setToolTipText(resourceMap.getString("gen_c.toolTipText")); // NOI18N
        gen_c.setName("gen_c"); // NOI18N
        gen_c.setBounds(150, 20, 110, 30);
        gen_button_pane1.add(gen_c, javax.swing.JLayeredPane.DEFAULT_LAYER);

        exit.setAction(actionMap.get("quit")); // NOI18N
        exit.setName("exit"); // NOI18N
        exit.setBounds(150, 80, 110, 30);
        gen_button_pane1.add(exit, javax.swing.JLayeredPane.DEFAULT_LAYER);

        gen_md.setAction(actionMap.get("gen")); // NOI18N
        gen_md.setText(resourceMap.getString("gen_md.text")); // NOI18N
        gen_md.setToolTipText(resourceMap.getString("gen_md.toolTipText")); // NOI18N
        gen_md.setName("gen_md"); // NOI18N
        gen_md.setBounds(20, 20, 110, 30);
        gen_button_pane1.add(gen_md, javax.swing.JLayeredPane.DEFAULT_LAYER);

        gen_h.setAction(actionMap.get("gen_h_file")); // NOI18N
        gen_h.setText(resourceMap.getString("gen_h.text")); // NOI18N
        gen_h.setToolTipText(resourceMap.getString("gen_h.toolTipText")); // NOI18N
        gen_h.setName("gen_h"); // NOI18N
        gen_h.setBounds(20, 80, 110, 30);
        gen_button_pane1.add(gen_h, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(insn_type_tab_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gen_button_pane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(insn_type_tab_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gen_button_pane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(info)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)))
                .addGap(8, 8, 8))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 628, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void add_checkadd_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_checkadd_info
        // TODO add your handling code here:
        insn_info="Add operand 2 to operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_add_checkadd_info

    private void add_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_add_checkdefault_info

    private void add_checkadd_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_add_checkadd_change
        // TODO add your handling code here:
        if(add_check.isSelected()) {
            add_si_check.setEnabled(true);
            add_qi_check.setEnabled(true);
            add_hi_check.setEnabled(true);
        }
        if(!add_check.isSelected()) {
            add_si_check.setEnabled(false);     add_si_check.setSelected(false);
            add_qi_check.setEnabled(false);     add_qi_check.setSelected(false);
            add_hi_check.setEnabled(false);     add_hi_check.setSelected(false);
        }
}//GEN-LAST:event_add_checkadd_change

    private void add_si_checkadd_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_si_checkadd_info
        // TODO add your handling code here:
        insn_info="Add operand 2 to operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_add_si_checkadd_info

    private void add_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_add_si_checkdefault_info

    private void add_qi_checkadd_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_qi_checkadd_info
        // TODO add your handling code here:
        insn_info="Add operand 2 to operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_add_qi_checkadd_info

    private void add_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_add_qi_checkdefault_info

    private void add_hi_checkadd_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_hi_checkadd_info
        // TODO add your handling code here:
        insn_info="Add operand 2 to operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_add_hi_checkadd_info

    private void add_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_add_hi_checkdefault_info

    private void add_paneadd_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_paneadd_info
        // TODO add your handling code here:
        insn_info="Add operand 2 to operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_add_paneadd_info

    private void add_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_add_panedefault_info

    private void sub_checksub_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_checksub_info
        // TODO add your handling code here:
        insn_info="Subtract operand 2 from operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_sub_checksub_info

    private void sub_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_sub_checkdefault_info

    private void sub_checksub_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sub_checksub_change
        // TODO add your handling code here:
        if(sub_check.isSelected()) {
            sub_si_check.setEnabled(!false);
            sub_qi_check.setEnabled(!false);
            sub_hi_check.setEnabled(!false);
        }
        if(!sub_check.isSelected()) {
            sub_si_check.setEnabled(!true);     sub_si_check.setSelected(false);
            sub_qi_check.setEnabled(!true);     sub_qi_check.setSelected(false);
            sub_hi_check.setEnabled(!true);     sub_hi_check.setSelected(false);
        }
}//GEN-LAST:event_sub_checksub_change

    private void sub_si_checksub_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_si_checksub_info
        // TODO add your handling code here:
        insn_info="Subtract operand 2 from operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_sub_si_checksub_info

    private void sub_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_sub_si_checkdefault_info

    private void sub_qi_checksub_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_qi_checksub_info
        // TODO add your handling code here:
        insn_info="Subtract operand 2 from operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_sub_qi_checksub_info

    private void sub_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_sub_qi_checkdefault_info

    private void sub_hi_checksub_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_hi_checksub_info
        // TODO add your handling code here:
        insn_info="Subtract operand 2 from operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_sub_hi_checksub_info

    private void sub_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_sub_hi_checkdefault_info

    private void sub_panesub_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_panesub_info
        // TODO add your handling code here:
        insn_info="Subtract operand 2 from operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_sub_panesub_info

    private void sub_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sub_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_sub_panedefault_info

    private void div_checkdiv_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_div_checkdiv_info
        // TODO add your handling code here:
        insn_info="Divide operand 2 by operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_div_checkdiv_info

    private void div_checkdiv_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_div_checkdiv_change
        // TODO add your handling code here:
        if(div_check.isSelected()) {
            div_si_check.setEnabled(!false);
            div_qi_check.setEnabled(!false);
            div_hi_check.setEnabled(!false);
        }
        if(!div_check.isSelected()) {
            div_si_check.setEnabled(!true);     div_si_check.setSelected(false);
            div_qi_check.setEnabled(!true);     div_qi_check.setSelected(false);
            div_hi_check.setEnabled(!true);     div_hi_check.setSelected(false);
        }
}//GEN-LAST:event_div_checkdiv_change

    private void div_si_checkdiv_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_div_si_checkdiv_info
        // TODO add your handling code here:
        insn_info="Divide operand 2 by operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_div_si_checkdiv_info

    private void div_qi_checkdiv_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_div_qi_checkdiv_info
        // TODO add your handling code here:
        insn_info="Divide operand 2 by operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_div_qi_checkdiv_info

    private void div_hi_checkdiv_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_div_hi_checkdiv_info
        // TODO add your handling code here:
        insn_info="Divide operand 2 by operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_div_hi_checkdiv_info

    private void div_panediv_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_div_panediv_info
        // TODO add your handling code here:
        insn_info="Divide operand 2 by operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_div_panediv_info

    private void div_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_div_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_div_panedefault_info

    private void mult_checkmult_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mult_checkmult_info
        // TODO add your handling code here:
        insn_info="Multiply operand 2 and operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mult_checkmult_info

    private void mult_checkmult_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mult_checkmult_change
        // TODO add your handling code here:
        if(mult_check.isSelected()) {
            mult_si_check.setEnabled(!false);
            mult_qi_check.setEnabled(!false);
            mult_hi_check.setEnabled(!false);
        }
        if(!mult_check.isSelected()) {
            mult_si_check.setEnabled(!true);    mult_si_check.setSelected(false);
            mult_qi_check.setEnabled(!true);    mult_qi_check.setSelected(false);
            mult_hi_check.setEnabled(!true);    mult_hi_check.setSelected(false);
        }
}//GEN-LAST:event_mult_checkmult_change

    private void mult_si_checkmult_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mult_si_checkmult_info
        // TODO add your handling code here:
        insn_info="Multiply operand 2 and operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mult_si_checkmult_info

    private void mult_qi_checkmult_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mult_qi_checkmult_info
        // TODO add your handling code here:
        insn_info="Multiply operand 2 and operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mult_qi_checkmult_info

    private void mult_hi_checkmult_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mult_hi_checkmult_info
        // TODO add your handling code here:
        insn_info="Multiply operand 2 and operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mult_hi_checkmult_info

    private void mult_panemult_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mult_panemult_info
        // TODO add your handling code here:
        insn_info="Multiply operand 2 and operand 1, storing the result in operand 0. All operands must have mode m.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mult_panemult_info

    private void mult_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mult_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mult_panedefault_info

    private void abs_checkabs_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abs_checkabs_info
        // TODO add your handling code here:
        insn_info="Store the absolute value of operand 1 into operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_abs_checkabs_info

    private void abs_checkabs_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_abs_checkabs_change
        // TODO add your handling code here:
        if(abs_check.isSelected()) {
            abs_si_check.setEnabled(!false);
            abs_qi_check.setEnabled(!false);
            abs_hi_check.setEnabled(!false);
        }
        if(!abs_check.isSelected()) {
            abs_si_check.setEnabled(!true);     abs_si_check.setSelected(false);
            abs_qi_check.setEnabled(!true);     abs_qi_check.setSelected(false);
            abs_hi_check.setEnabled(!true);     abs_hi_check.setSelected(false);
        }
}//GEN-LAST:event_abs_checkabs_change

    private void abs_si_checkabs_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abs_si_checkabs_info
        // TODO add your handling code here:
        insn_info="Store the absolute value of operand 1 into operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_abs_si_checkabs_info

    private void abs_qi_checkabs_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abs_qi_checkabs_info
        // TODO add your handling code here:
        insn_info="Store the absolute value of operand 1 into operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_abs_qi_checkabs_info

    private void abs_hi_checkabs_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abs_hi_checkabs_info
        // TODO add your handling code here:
        insn_info="Store the absolute value of operand 1 into operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_abs_hi_checkabs_info

    private void abs_paneabs_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abs_paneabs_info
        // TODO add your handling code here:
        insn_info="Store the absolute value of operand 1 into operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_abs_paneabs_info

    private void abs_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abs_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_abs_panedefault_info

    private void mod_checkmod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_checkmod_info
        // TODO add your handling code here:
        insn_info="Stores the remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mod_checkmod_info

    private void mod_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mod_checkdefault_info

    private void mod_checkmod_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mod_checkmod_change
        // TODO add your handling code here:
        if(mod_check.isSelected()) {
            mod_si_check.setEnabled(!false);
            mod_qi_check.setEnabled(!false);
            mod_hi_check.setEnabled(!false);
        }
        if(!mod_check.isSelected()) {
            mod_si_check.setEnabled(!true);     mod_si_check.setSelected(false);
            mod_qi_check.setEnabled(!true);     mod_qi_check.setSelected(false);
            mod_hi_check.setEnabled(!true);     mod_hi_check.setSelected(false);
        }
}//GEN-LAST:event_mod_checkmod_change

    private void mod_si_checkmod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_si_checkmod_info
        // TODO add your handling code here:
        insn_info="Stores the remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mod_si_checkmod_info

    private void mod_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mod_si_checkdefault_info

    private void mod_qi_checkmod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_qi_checkmod_info
        // TODO add your handling code here:
        insn_info="Stores the remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mod_qi_checkmod_info

    private void mod_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mod_qi_checkdefault_info

    private void mod_hi_checkmod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_hi_checkmod_info
        // TODO add your handling code here:
        insn_info="Stores the remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mod_hi_checkmod_info

    private void mod_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mod_hi_checkdefault_info

    private void mod_panemod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_panemod_info
        // TODO add your handling code here:
        insn_info="Stores the remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mod_panemod_info

    private void mod_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mod_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mod_panedefault_info

    private void umod_checkumod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_checkumod_info
        // TODO add your handling code here:
        insn_info="Stores the absolute value of remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_umod_checkumod_info

    private void umod_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_umod_checkdefault_info

    private void umod_checkumod_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_umod_checkumod_change
        // TODO add your handling code here:
        if(umod_check.isSelected()) {
            umod_si_check.setEnabled(!false);
            umod_qi_check.setEnabled(!false);
            umod_hi_check.setEnabled(!false);
        }
        if(!umod_check.isSelected()) {
            umod_si_check.setEnabled(!true);    umod_si_check.setSelected(false);
            umod_qi_check.setEnabled(!true);    umod_qi_check.setSelected(false);
            umod_hi_check.setEnabled(!true);    umod_hi_check.setSelected(false);
        }
}//GEN-LAST:event_umod_checkumod_change

    private void umod_si_checkumod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_si_checkumod_info
        // TODO add your handling code here:
        insn_info="Stores the absolute value of remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_umod_si_checkumod_info

    private void umod_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_umod_si_checkdefault_info

    private void umod_qi_checkumod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_qi_checkumod_info
        // TODO add your handling code here:
        insn_info="Stores the absolute value of remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_umod_qi_checkumod_info

    private void umod_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_umod_qi_checkdefault_info

    private void umod_hi_checkumod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_hi_checkumod_info
        // TODO add your handling code here:
        insn_info="Stores the absolute value of remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_umod_hi_checkumod_info

    private void umod_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_umod_hi_checkdefault_info

    private void umod_paneumod_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_paneumod_info
        // TODO add your handling code here:
        insn_info="Stores the absolute value of remainder after division of operand 1 by operand 0 in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_umod_paneumod_info

    private void umod_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_umod_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_umod_panedefault_info

    private void and_checkand_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_and_checkand_info
        // TODO add your handling code here:
        insn_info="AND operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_and_checkand_info

    private void and_checkand_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_and_checkand_change
        // TODO add your handling code here:
        if(and_check.isSelected()) {
            and_si_check.setEnabled(!false);
            and_qi_check.setEnabled(!false);
            and_hi_check.setEnabled(!false);
        }
        if(!and_check.isSelected()) {
            and_si_check.setEnabled(!true);     and_si_check.setSelected(false);
            and_qi_check.setEnabled(!true);     and_qi_check.setSelected(false);
            and_hi_check.setEnabled(!true);     and_hi_check.setSelected(false);
        }
}//GEN-LAST:event_and_checkand_change

    private void and_si_checkand_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_and_si_checkand_info
        // TODO add your handling code here:
        insn_info="AND operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_and_si_checkand_info

    private void and_qi_checkand_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_and_qi_checkand_info
        // TODO add your handling code here:
        insn_info="AND operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_and_qi_checkand_info

    private void and_hi_checkand_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_and_hi_checkand_info
        // TODO add your handling code here:
        insn_info="AND operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_and_hi_checkand_info

    private void and_paneand_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_and_paneand_info
        // TODO add your handling code here:
        insn_info="AND operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_and_paneand_info

    private void and_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_and_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_and_panedefault_info

    private void or_checkor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_checkor_info
        // TODO add your handling code here:
        insn_info="OR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_or_checkor_info

    private void or_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_or_checkdefault_info

    private void or_checkor_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_or_checkor_change
        // TODO add your handling code here:
        if(or_check.isSelected()) {
            or_si_check.setEnabled(!false);
            or_qi_check.setEnabled(!false);
            or_hi_check.setEnabled(!false);
        }
        if(!or_check.isSelected()) {
            or_si_check.setEnabled(!true);      or_si_check.setSelected(false);
            or_qi_check.setEnabled(!true);      or_qi_check.setSelected(false);
            or_hi_check.setEnabled(!true);      or_hi_check.setSelected(false);
        }
}//GEN-LAST:event_or_checkor_change

    private void or_si_checkor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_si_checkor_info
        // TODO add your handling code here:
        insn_info="OR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_or_si_checkor_info

    private void or_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_or_si_checkdefault_info

    private void or_qi_checkor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_qi_checkor_info
        // TODO add your handling code here:
        insn_info="OR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_or_qi_checkor_info

    private void or_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_or_qi_checkdefault_info

    private void or_hi_checkor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_hi_checkor_info
        // TODO add your handling code here:
        insn_info="OR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_or_hi_checkor_info

    private void or_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_or_hi_checkdefault_info

    private void or_paneor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_paneor_info
        // TODO add your handling code here:
        insn_info="OR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_or_paneor_info

    private void or_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_or_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_or_panedefault_info

    private void xor_checkxor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_checkxor_info
        // TODO add your handling code here:
        insn_info="XOR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_xor_checkxor_info

    private void xor_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_xor_checkdefault_info

    private void xor_checkxor_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xor_checkxor_change
        // TODO add your handling code here:
        if(xor_check.isSelected()) {
            xor_si_check.setEnabled(!false);
            xor_qi_check.setEnabled(!false);
            xor_hi_check.setEnabled(!false);
        }
        if(!xor_check.isSelected()) {
            xor_si_check.setEnabled(!true);     xor_si_check.setSelected(false);
            xor_qi_check.setEnabled(!true);     xor_qi_check.setSelected(false);
            xor_hi_check.setEnabled(!true);     xor_hi_check.setSelected(false);
        }
}//GEN-LAST:event_xor_checkxor_change

    private void xor_si_checkxor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_si_checkxor_info
        // TODO add your handling code here:
        insn_info="XOR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_xor_si_checkxor_info

    private void xor_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_xor_si_checkdefault_info

    private void xor_qi_checkxor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_qi_checkxor_info
        // TODO add your handling code here:
        insn_info="XOR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_xor_qi_checkxor_info

    private void xor_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_xor_qi_checkdefault_info

    private void xor_hi_checkxor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_hi_checkxor_info
        // TODO add your handling code here:
        insn_info="XOR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_xor_hi_checkxor_info

    private void xor_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_xor_hi_checkdefault_info

    private void xor_panexor_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_panexor_info
        // TODO add your handling code here:
        insn_info="XOR operand 0 and operand 1. Result is stored in operand 2.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_xor_panexor_info

    private void xor_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xor_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_xor_panedefault_info

    private void ashl_checkashl_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashl_checkashl_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 left by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashl_checkashl_info

    private void ashl_checkashl_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ashl_checkashl_change
        // TODO add your handling code here:
        if(ashl_check.isSelected()) {
            ashl_si_check.setEnabled(!false);
            ashl_qi_check.setEnabled(!false);
            ashl_hi_check.setEnabled(!false);
        }
        if(!ashl_check.isSelected()) {
            ashl_si_check.setEnabled(!true);    ashl_si_check.setSelected(false);
            ashl_qi_check.setEnabled(!true);    ashl_qi_check.setSelected(false);
            ashl_hi_check.setEnabled(!true);    ashl_hi_check.setSelected(false);
        }
}//GEN-LAST:event_ashl_checkashl_change

    private void ashl_si_checkashl_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashl_si_checkashl_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 left by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashl_si_checkashl_info

    private void ashl_qi_checkashl_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashl_qi_checkashl_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 left by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashl_qi_checkashl_info

    private void ashl_hi_checkashl_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashl_hi_checkashl_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 left by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashl_hi_checkashl_info

    private void ashl_paneashl_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashl_paneashl_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 left by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashl_paneashl_info

    private void ashl_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashl_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_ashl_panedefault_info

    private void ashr_checkashr_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashr_checkashr_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 right by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashr_checkashr_info

    private void ashr_checkashr_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ashr_checkashr_change
        // TODO add your handling code here:
        if(ashr_check.isSelected()) {
            ashr_si_check.setEnabled(!false);
            ashr_qi_check.setEnabled(!false);
            ashr_hi_check.setEnabled(!false);
        }
        if(!ashr_check.isSelected()) {
            ashr_si_check.setEnabled(!true);    ashr_si_check.setSelected(false);
            ashr_qi_check.setEnabled(!true);    ashr_qi_check.setSelected(false);
            ashr_hi_check.setEnabled(!true);    ashr_hi_check.setSelected(false);
        }
}//GEN-LAST:event_ashr_checkashr_change

    private void ashr_si_checkashr_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashr_si_checkashr_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 right by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashr_si_checkashr_info

    private void ashr_qi_checkashr_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashr_qi_checkashr_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 right by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashr_qi_checkashr_info

    private void ashr_hi_checkashr_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashr_hi_checkashr_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 right by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashr_hi_checkashr_info

    private void ashr_paneashr_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashr_paneashr_info
        // TODO add your handling code here:
        insn_info="Arithmetic-shift operand 1 right by a number of bits specified by operand 2, and store the result in operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ashr_paneashr_info

    private void ashr_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ashr_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_ashr_panedefault_info

    private void mov_checkmov_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_checkmov_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mov_checkmov_info

    private void mov_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mov_checkdefault_info

    private void mov_checkmov_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mov_checkmov_change
        // TODO add your handling code here:
        if(mov_check.isSelected()) {
            mov_si_check.setEnabled(!false);
            mov_qi_check.setEnabled(!false);
            mov_hi_check.setEnabled(!false);
        }
        if(!mov_check.isSelected()) {
            mov_si_check.setEnabled(!true);     mov_si_check.setSelected(false);
            mov_qi_check.setEnabled(!true);     mov_qi_check.setSelected(false);
            mov_hi_check.setEnabled(!true);     mov_hi_check.setSelected(false);
        }
}//GEN-LAST:event_mov_checkmov_change

    private void mov_si_checkmov_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_si_checkmov_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mov_si_checkmov_info

    private void mov_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mov_si_checkdefault_info

    private void mov_qi_checkmov_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_qi_checkmov_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mov_qi_checkmov_info

    private void mov_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mov_qi_checkdefault_info

    private void mov_hi_checkmov_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_hi_checkmov_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mov_hi_checkmov_info

    private void mov_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mov_hi_checkdefault_info

    private void mov_panemov_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_panemov_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_mov_panemov_info

    private void mov_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mov_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_mov_panedefault_info

    private void load_checkload_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_checkload_info
        // TODO add your handling code here:
        insn_info="Move contents of memory loacation as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_load_checkload_info

    private void load_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_load_checkdefault_info

    private void load_checkload_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_load_checkload_change
        // TODO add your handling code here:
        if(load_check.isSelected()) {
            load_si_check.setEnabled(!false);
            load_qi_check.setEnabled(!false);
            load_hi_check.setEnabled(!false);
        }
        if(!load_check.isSelected()) {
            load_si_check.setEnabled(!true);    load_si_check.setSelected(false);
            load_qi_check.setEnabled(!true);    load_qi_check.setSelected(false);
            load_hi_check.setEnabled(!true);    load_hi_check.setSelected(false);
        }
}//GEN-LAST:event_load_checkload_change

    private void load_si_checkload_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_si_checkload_info
        // TODO add your handling code here:
        insn_info="Move contents of memory loacation as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_load_si_checkload_info

    private void load_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_load_si_checkdefault_info

    private void load_qi_checkload_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_qi_checkload_info
        // TODO add your handling code here:
        insn_info="Move contents of memory loacation as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_load_qi_checkload_info

    private void load_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_load_qi_checkdefault_info

    private void load_hi_checkload_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_hi_checkload_info
        // TODO add your handling code here:
        insn_info="Move contents of memory loacation as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_load_hi_checkload_info

    private void load_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_load_hi_checkdefault_info

    private void load_paneload_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_paneload_info
        // TODO add your handling code here:
        insn_info="Move contents of memory loacation as operand 1 to register as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_load_paneload_info

    private void load_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_load_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_load_panedefault_info

    private void store_checkstore_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_checkstore_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to memory location as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_store_checkstore_info

    private void store_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_store_checkdefault_info

    private void store_checkstore_change(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_store_checkstore_change
        // TODO add your handling code here:
        if(store_check.isSelected()) {
            store_si_check.setEnabled(!false);
            store_qi_check.setEnabled(!false);
            store_hi_check.setEnabled(!false);
        }
        if(!store_check.isSelected()) {
            store_si_check.setEnabled(!true);   store_si_check.setSelected(false);
            store_qi_check.setEnabled(!true);   store_qi_check.setSelected(false);
            store_hi_check.setEnabled(!true);   store_hi_check.setSelected(false);
        }
}//GEN-LAST:event_store_checkstore_change

    private void store_si_checkstore_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_si_checkstore_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to memory location as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_store_si_checkstore_info

    private void store_si_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_si_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_store_si_checkdefault_info

    private void store_qi_checkstore_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_qi_checkstore_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to memory location as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_store_qi_checkstore_info

    private void store_qi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_qi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_store_qi_checkdefault_info

    private void store_hi_checkstore_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_hi_checkstore_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to memory location as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_store_hi_checkstore_info

    private void store_hi_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_hi_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_store_hi_checkdefault_info

    private void store_panestore_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_panestore_info
        // TODO add your handling code here:
        insn_info="Move contents of register as operand 1 to memory location as operand 0.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_store_panestore_info

    private void store_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_store_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_store_panedefault_info

    private void jump_checkjump_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jump_checkjump_info
        // TODO add your handling code here:
        insn_info="A jump inside a function; an unconditional branch. Operand 0 is the label_ref of the label to jump to.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_jump_checkjump_info

    private void jump_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jump_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_jump_checkdefault_info

    private void jump_panejump_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jump_panejump_info
        // TODO add your handling code here:
        insn_info="A jump inside a function; an unconditional branch. Operand 0 is the label_ref of the label to jump to.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_jump_panejump_info

    private void jump_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jump_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_jump_panedefault_info

    private void ijump_checkijump_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ijump_checkijump_info
        // TODO add your handling code here:
        insn_info="An instruction to jump to an address which is operand zero. ";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ijump_checkijump_info

    private void ijump_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ijump_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_ijump_checkdefault_info

    private void ijump_paneijump_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ijump_paneijump_info
        // TODO add your handling code here:
        insn_info="An instruction to jump to an address which is operand zero. ";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_ijump_paneijump_info

    private void ijump_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ijump_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_ijump_panedefault_info

    private void epilogue_checkepi_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_epilogue_checkepi_info
        // TODO add your handling code here:
        insn_info="This pattern emits RTL for exit from a function. The function exit is responsible for deallocating the stack frame, restoring callee saved registers and emitting the return instruction.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_epilogue_checkepi_info

    private void epilogue_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_epilogue_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_epilogue_checkdefault_info

    private void epilogue_paneepi_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_epilogue_paneepi_info
        // TODO add your handling code here:
        insn_info="This pattern emits RTL for exit from a function. The function exit is responsible for deallocating the stack frame, restoring callee saved registers and emitting the return instruction.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_epilogue_paneepi_info

    private void epilogue_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_epilogue_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_epilogue_panedefault_info

    private void prologue_checkpro_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prologue_checkpro_info
        // TODO add your handling code here:
        insn_info="This pattern, if defined, emits RTL for entry to a function. The function entry is responsible for setting up the stack frame, initializing the frame pointer register, saving callee saved registers, etc.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_prologue_checkpro_info

    private void prologue_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prologue_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_prologue_checkdefault_info

    private void prologue_panepro_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prologue_panepro_info
        // TODO add your handling code here:
        insn_info="This pattern, if defined, emits RTL for entry to a function. The function entry is responsible for setting up the stack frame, initializing the frame pointer register, saving callee saved registers, etc.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_prologue_panepro_info

    private void prologue_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prologue_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_prologue_panedefault_info

    private void call_checkcall_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_call_checkcall_info
        // TODO add your handling code here:
        insn_info="Subroutine call instruction returning no value. Operand 0 is the function to call; operand 1 is the number of bytes of arguments pushed as a const_int; operand 2 is the number of registers used as operands.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_call_checkcall_info

    private void call_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_call_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_call_checkdefault_info

    private void call_panecall_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_call_panecall_info
        // TODO add your handling code here:
        insn_info="Subroutine call instruction returning no value. Operand 0 is the function to call; operand 1 is the number of bytes of arguments pushed as a const_int; operand 2 is the number of registers used as operands.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_call_panecall_info

    private void call_panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_call_panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_call_panedefault_info

    private void call_value_checkcall_value_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_call_value_checkcall_value_info
        // TODO add your handling code here:
        insn_info="Subroutine call instruction returning a value. Operand 0 is the hard register in which the value is returned. There are three more operands, the same as the three operands of the `call' instruction).";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_call_value_checkcall_value_info

    private void call_value_checkdefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_call_value_checkdefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_call_value_checkdefault_info

    private void call_value__panecall_value_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_call_value__panecall_value_info
        // TODO add your handling code here:
        insn_info="Subroutine call instruction returning a value. Operand 0 is the hard register in which the value is returned. There are three more operands, the same as the three operands of the `call' instruction.";
        info_text_area.setText(insn_info);
}//GEN-LAST:event_call_value__panecall_value_info

    private void call_value__panedefault_info(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_call_value__panedefault_info
        // TODO add your handling code here:
        info_text_area.setText(default_info);
}//GEN-LAST:event_call_value__panedefault_info

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox abs_check;
    private javax.swing.JCheckBox abs_hi_check;
    private javax.swing.JLayeredPane abs_pane;
    private javax.swing.JCheckBox abs_qi_check;
    private javax.swing.JCheckBox abs_si_check;
    private javax.swing.JCheckBox add_check;
    private javax.swing.JCheckBox add_hi_check;
    private javax.swing.JLayeredPane add_pane;
    private javax.swing.JCheckBox add_qi_check;
    private javax.swing.JCheckBox add_si_check;
    private javax.swing.JCheckBox and_check;
    private javax.swing.JCheckBox and_hi_check;
    private javax.swing.JLayeredPane and_pane;
    private javax.swing.JCheckBox and_qi_check;
    private javax.swing.JCheckBox and_si_check;
    private javax.swing.JLayeredPane arithmetic_insn_tab;
    private javax.swing.JLabel arithmetic_label;
    private javax.swing.JSeparator arithmetic_separator;
    private javax.swing.JCheckBox ashl_check;
    private javax.swing.JCheckBox ashl_hi_check;
    private javax.swing.JLayeredPane ashl_pane;
    private javax.swing.JCheckBox ashl_qi_check;
    private javax.swing.JCheckBox ashl_si_check;
    private javax.swing.JCheckBox ashr_check;
    private javax.swing.JCheckBox ashr_hi_check;
    private javax.swing.JLayeredPane ashr_pane;
    private javax.swing.JCheckBox ashr_qi_check;
    private javax.swing.JCheckBox ashr_si_check;
    private javax.swing.JButton back;
    private javax.swing.JButton back2;
    private javax.swing.JButton back3;
    private javax.swing.JButton back4;
    private javax.swing.JCheckBox call_check;
    private javax.swing.JLayeredPane call_pane;
    private javax.swing.JLayeredPane call_value__pane;
    private javax.swing.JCheckBox call_value_check;
    private javax.swing.JButton clear_all;
    private javax.swing.JLabel data_label;
    private javax.swing.JLayeredPane data_transfer_insn_tab;
    private javax.swing.JSeparator data_transfer_separator;
    private javax.swing.JCheckBox div_check;
    private javax.swing.JCheckBox div_hi_check;
    private javax.swing.JLayeredPane div_pane;
    private javax.swing.JCheckBox div_qi_check;
    private javax.swing.JCheckBox div_si_check;
    private javax.swing.JCheckBox epilogue_check;
    private javax.swing.JLayeredPane epilogue_pane;
    private javax.swing.JButton exit;
    private javax.swing.JLayeredPane gen_button_pane1;
    private javax.swing.JButton gen_c;
    private javax.swing.JButton gen_h;
    private javax.swing.JButton gen_md;
    private javax.swing.JCheckBox ijump_check;
    private javax.swing.JLayeredPane ijump_pane;
    private javax.swing.JScrollPane info;
    private javax.swing.JTextArea info_text_area;
    private javax.swing.JTabbedPane insn_type_tab_pane;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox15;
    private javax.swing.JComboBox jComboBox16;
    private javax.swing.JComboBox jComboBox17;
    private javax.swing.JComboBox jComboBox18;
    private javax.swing.JComboBox jComboBox19;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox20;
    private javax.swing.JComboBox jComboBox21;
    private javax.swing.JComboBox jComboBox22;
    private javax.swing.JComboBox jComboBox23;
    private javax.swing.JComboBox jComboBox24;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JCheckBox jump_check;
    private javax.swing.JLayeredPane jump_insn_tab;
    private javax.swing.JLabel jump_label;
    private javax.swing.JLayeredPane jump_pane;
    private javax.swing.JSeparator jump_separator;
    private javax.swing.JCheckBox load_check;
    private javax.swing.JCheckBox load_hi_check;
    private javax.swing.JLayeredPane load_pane;
    private javax.swing.JCheckBox load_qi_check;
    private javax.swing.JCheckBox load_si_check;
    private javax.swing.JLayeredPane logical_insn_tab;
    private javax.swing.JLabel logical_label;
    private javax.swing.JSeparator logical_separator;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JCheckBox mod_check;
    private javax.swing.JCheckBox mod_hi_check;
    private javax.swing.JLayeredPane mod_pane;
    private javax.swing.JCheckBox mod_qi_check;
    private javax.swing.JCheckBox mod_si_check;
    private javax.swing.JCheckBox mov_check;
    private javax.swing.JCheckBox mov_hi_check;
    private javax.swing.JLayeredPane mov_pane;
    private javax.swing.JCheckBox mov_qi_check;
    private javax.swing.JCheckBox mov_si_check;
    private javax.swing.JCheckBox mult_check;
    private javax.swing.JCheckBox mult_hi_check;
    private javax.swing.JLayeredPane mult_pane;
    private javax.swing.JCheckBox mult_qi_check;
    private javax.swing.JCheckBox mult_si_check;
    private javax.swing.JButton next;
    private javax.swing.JButton next1;
    private javax.swing.JButton next2;
    private javax.swing.JButton next3;
    private javax.swing.JCheckBox or_check;
    private javax.swing.JCheckBox or_hi_check;
    private javax.swing.JLayeredPane or_pane;
    private javax.swing.JCheckBox or_qi_check;
    private javax.swing.JCheckBox or_si_check;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JCheckBox prologue_check;
    private javax.swing.JLayeredPane prologue_pane;
    private javax.swing.JButton select_all_hi;
    private javax.swing.JButton select_all_qi;
    private javax.swing.JButton select_all_si;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JCheckBox store_check;
    private javax.swing.JCheckBox store_hi_check;
    private javax.swing.JLayeredPane store_pane;
    private javax.swing.JCheckBox store_qi_check;
    private javax.swing.JCheckBox store_si_check;
    private javax.swing.JCheckBox sub_check;
    private javax.swing.JCheckBox sub_hi_check;
    private javax.swing.JLayeredPane sub_pane;
    private javax.swing.JCheckBox sub_qi_check;
    private javax.swing.JCheckBox sub_si_check;
    private javax.swing.JCheckBox umod_check;
    private javax.swing.JCheckBox umod_hi_check;
    private javax.swing.JLayeredPane umod_pane;
    private javax.swing.JCheckBox umod_qi_check;
    private javax.swing.JCheckBox umod_si_check;
    private javax.swing.JCheckBox xor_check;
    private javax.swing.JCheckBox xor_hi_check;
    private javax.swing.JLayeredPane xor_pane;
    private javax.swing.JCheckBox xor_qi_check;
    private javax.swing.JCheckBox xor_si_check;
    // End of variables declaration//GEN-END:variables

    private JDialog aboutBox;
}
