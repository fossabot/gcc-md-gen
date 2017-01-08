package md_generator;

//This class contains the methods which will construct instruction
//patterns for instructions selected on GUI by the user.
//Instance of this class is created when user presses 'Generate MD' button
//on GUI. Various Instruction Parameters such as Operand Count, Standard
//Patter Names, Constraints on selected instruction are passed to instance of this
//class contained in ActionCommand of selcted insn check box.
//Jump and Functional instructins are treated specially (as they can't be generalised).

/*Example pattern:
(define_insn "addsi3"
	[(set (match_operand:SI 0 "register_operand" "=r,r")
              (plus:SI (match_operand:SI 1 "register_operand" "r,r")
                       (match_operand:SI 2 "nonmemory_operand" "r,K"))
         )]
        ""
        "@
         add \\t%0, %1, %2
         addi \\t%0, %1, %c2") */

//Core of the pattern generator.
public class gen_core
{
    private String insn_info,rtl_op1="set",rtl_op2="",insn_pattern="",md_construct="define_insn";
    private String machine_mode,spn,asm1="",asm2="";
    private int op_count,constraint_type,i,flag_asm2;

    //Holds individual template for each operand.
    private class rtl_temp
    {
        private String constraints="",predicate="",md_construct="match_operand",template;
        private int op_no;

        void rtl_temp(int curr_op_no)
        {
            op_no=curr_op_no;
            template="";
            constraints=""; predicate=""; md_construct="match_operand";
            set_predicates_constraints();

            form_template();
        }

        //Constructs a template that is contained inside a pattern.
        private void form_template()
        {
            template="("+md_construct+":"+machine_mode.toUpperCase()+" "+op_no+" \""+predicate+"\" \""+constraints+"\")";
        }

        //Return template for current operand to Main Pattern Generator.
        public String getTemplate()
        {
            return template;
        }

        //Sets predicates and constraints for current operand.
        private void set_predicates_constraints()
        {
            if(constraint_type==1)
            {
                if(op_no==0)
                {
                    predicate="register_operand";   constraints="=r,r";
                }
                else if(op_no==1)
                {
                    predicate="register_operand";   constraints="r";
                }
                else if(op_no==2)
                {
                    predicate="nonmemory_operand";  constraints="r,i";
                }
            }
            else if(constraint_type==2)
            {
                if(op_no==0)
                {
                    predicate="register_operand";   constraints="=r,r";
                }
                else if(op_no==1)
                {
                    predicate="register_operand";   constraints="r";
                }
                else if(op_no==2)
                {
                    predicate="register_operand";   constraints="r";
                }
            }
            else if(constraint_type==3)
            {
                if(op_no==0)
                {
                    predicate="register_operand";   constraints="=r,r";
                }
                else if(op_no==1)
                {
                    predicate="register_operand";   constraints="r";
                }
            }
            else if(constraint_type==4)
            {
                if(op_no==0)
                {
                    predicate="register_operand";   constraints="=r,r";
                }
                else if(op_no==1)
                {
                    predicate="memory_operand";     constraints="m";
                }
            }
            else if(constraint_type==5)
            {
                if(op_no==0)
                {
                    predicate="memory_operand";     constraints="=m,m";
                }
                else if(op_no==1)
                {
                    predicate="immediate_operand";  constraints="i";
                }
            }
            else if(constraint_type==6)
            {
                if(op_no==0)
                {
                    predicate="register_operand";   constraints="=r,r";
                }
                else if(op_no==1)
                {
                    predicate="memory_operand";     constraints="m";
                }
                else if(op_no==2)
                {
                    predicate="immediate_operand";  constraints="i";
                }
            }
            else if(constraint_type==7)
            {
                if(op_no==0)
                {
                    predicate="register_operand";   constraints="=r,r";
                }
                else if(op_no==1)
                {
                    predicate="nonmemory_operand";   constraints="r,i";
                }
            }
            else if(constraint_type==8)
            {
                if(op_no==0)
                {
                    predicate="register_operand";   constraints="r";
                }
            }
            else if(constraint_type==9)
            {
                if(op_no==0)
                {
                    predicate="memory_operand";   constraints="m";
                }
            }
        }
    }

    //Clears previous instruction data.
    public void flush()
    {
        insn_info="";   rtl_op1="set";  rtl_op2=""; insn_pattern="";    md_construct="define_insn";
        machine_mode="";    spn=""; asm1="";    asm2="";
    }

    //Main pattern generator.
    public String gen_core(String insn_info_ui,String machine_mode_ui)
    {
         machine_mode=machine_mode_ui;
         insn_info=insn_info_ui;
         exctract_insn_info();
         set_operators_asm();
         rtl_temp[] template=new rtl_temp[op_count];    //Initialize templates for each operand.

         if(asm2.equalsIgnoreCase(""))                  //Sets a flag for asm 2 to determine hence onwards whether to use it or not.
             flag_asm2=0;
         else
             flag_asm2=1;
         for(i=0;i<op_count;i++)                        //Constructs each template for each operand.
         {
             template[i]=new rtl_temp();
             template[i].rtl_temp(i);
             if(i<op_count-1)
                asm1=asm1+"%"+i+", ";                   //Build asm1.
             if(flag_asm2==1)
                 if(i<op_count-1)
                    asm2=asm2+"%"+i+", ";               //Build asm2 if flag for it is 1.
         }
         asm1=asm1+"%"+(i-1);                           //Appends final operand number to asm1.
         if(flag_asm2==1)
             asm2=asm2+"%"+(i-1);                       //Appends final operand number to asm2.

         //Following code builds final pattern.
         insn_pattern="("+md_construct+" \""+spn+machine_mode+op_count+"\"\n\t[("+rtl_op1+" "+template[0].getTemplate();
         if(rtl_op2.equalsIgnoreCase(""))
         {
            //insn_pattern=insn_pattern+"\n\t\t  "+template[1].getTemplate()+"\n\t)]\n\t\"\" \n\t\"@\n\t"+asm1+")";
             if(asm2.equalsIgnoreCase(""))
                 insn_pattern=insn_pattern+"\n\t\t  "+template[1].getTemplate()+"\n\t)]\n\t\"\"\n\t\""+asm1+")";
             else
                 insn_pattern=insn_pattern+"\n\t\t  "+template[1].getTemplate()+"\n\t)]\n\t\"\" \n\t\"@\n\t "+asm1+"\n\t"+asm2+")";
         }
         else
         {
            //insn_pattern=insn_pattern+"\n\t\t  ("+rtl_op2+":"+machine_mode.toUpperCase()+" "+template[1].getTemplate()+"\n\t\t\t\t   "+template[2].getTemplate()+")"+"\n\t)]\n\t\"\"\n\t\"@\n\t"+asm1+")";
             if(spn.equalsIgnoreCase("abs"))
                     insn_pattern=insn_pattern+"\n\t\t  ("+rtl_op2+":"+machine_mode.toUpperCase()+" "+template[1].getTemplate()+"\n\t)]\n\t\"\"\n\t\""+asm1+")";
             else if(asm2.equalsIgnoreCase(""))
                insn_pattern=insn_pattern+"\n\t\t  ("+rtl_op2+":"+machine_mode.toUpperCase()+" "+template[1].getTemplate()+"\n\t\t\t\t   "+template[2].getTemplate()+")"+"\n\t)]\n\t\"\"\n\t\""+asm1+")";
             else
                insn_pattern=insn_pattern+"\n\t\t  ("+rtl_op2+":"+machine_mode.toUpperCase()+" "+template[1].getTemplate()+"\n\t\t\t\t   "+template[2].getTemplate()+")"+"\n\t)]\n\t\"\"\n\t\"@\n\t "+asm1+"\n\t "+asm2+")";
        }
         //Returns final pattern to GUI.
        return insn_pattern;
    }

    //Exctracts various insn parameters from action command.
    private void exctract_insn_info()
    {
        int doll_index,hash_index;
        doll_index=insn_info.indexOf("$");
        hash_index=insn_info.indexOf("#");

        spn=insn_info.substring(0, doll_index);     //Exctract Standard Pattern Name from ActionCommand
        op_count=Integer.parseInt(insn_info.substring(doll_index+1, doll_index+2));     //Exctract Operand Count from ActionCommand
        constraint_type=Integer.parseInt(insn_info.substring(hash_index+1, hash_index+2));      //Exctract Constraint Type from ActionCommand
    }

    //Sets rtl operators for each instruction.
    private void set_operators_asm()
    {
        if(spn.equalsIgnoreCase("add"))
        {
            rtl_op2="plus"; asm1="add \\\\t"; asm2="addi \\\\t";
        }
        else if(spn.equalsIgnoreCase("sub"))
        {
            rtl_op2="sub";  asm1="sub \\\\t"; asm2="subi \\\\t";
        }
        else if(spn.equalsIgnoreCase("mul"))
        {
            rtl_op2="mult"; asm1="mul \\\\t";
        }
        else if(spn.equalsIgnoreCase("div"))
        {
            rtl_op2="div";  asm1="div \\\\t";
        }
        else if(spn.equalsIgnoreCase("abs"))
        {
            rtl_op2="abs";  asm1="abs \\\\t";
        }
        else if(spn.equalsIgnoreCase("mod"))
        {
            rtl_op2="mod";  asm1="rem \\\\t";
        }
        else if(spn.equalsIgnoreCase("umod"))
        {
            rtl_op2="umod"; asm1="urem \\\\t";
        }
        else if(spn.equalsIgnoreCase("ior"))
        {
            rtl_op2="ior";  asm1="or \\\\t";  asm2="ori \\\\t";
        }
        else if(spn.equalsIgnoreCase("xor"))
        {
            rtl_op2="xor";  asm1="xor \\\\t"; asm2="xori \\\\t";
        }
        else if(spn.equalsIgnoreCase("and"))
        {
            rtl_op2="and";  asm1="and \\\\t"; asm2="andi \\\\t";
        }
        else if(spn.equalsIgnoreCase("ashl"))
        {
            rtl_op2="ashift";   asm1="shll \\\\t";
        }
        else if(spn.equalsIgnoreCase("ashr"))
        {
            rtl_op2="ashiftrt"; asm1="sra \\\\t";
        }
        else if(spn.equalsIgnoreCase("mov"))
            asm1="mov \\\\t";

        else if(spn.equalsIgnoreCase("load"))
            asm1="lw \\\\t";

        else if(spn.equalsIgnoreCase("store"))
            asm1="sw \\\\t";

        else if(spn.equalsIgnoreCase("jump"))
            asm1=" \\\\t";

        else if(spn.equalsIgnoreCase("indirect_jump"))
            asm1="jr \\\\t";
    }
}