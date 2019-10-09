package com.jackandphantom.demon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button previous, play, next,screen,p,n,q,w,e,r,t,y,u,i,o,a,s,d,f,g,h,j,k,l,z,x,c,v,b,alt,shift,arrow_up,arrow_down,enter;
    Context context;
    TextView playPauseButton;
    TextView nextButton;
    TextView previousButton;
    ImageView image;
    TextView mousePad;

    private boolean isConnected=false;
    private boolean mouseMoved=false;
    private Socket socket;
    private PrintWriter out;



    private float initX =0;
    private float initY =0;
    private float disX =0;
    private float disY =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        context = this; //save the context to show Toast messages

        //Get references of all buttons
        image = findViewById(R.id.image);
        p = (Button) findViewById(R.id.p);
        n = (Button) findViewById(R.id.n);
        q = (Button) findViewById(R.id.q);
        w = (Button) findViewById(R.id.w);
        e = (Button) findViewById(R.id.e);
        arrow_up = (Button) findViewById(R.id.arrow_up);
        r = (Button) findViewById(R.id.r);
        t = (Button) findViewById(R.id.t);
        y = (Button) findViewById(R.id.y);
        u = (Button) findViewById(R.id.u);
        i = (Button) findViewById(R.id.i);
        o = (Button) findViewById(R.id.o);
        a = (Button) findViewById(R.id.a);
        s = (Button) findViewById(R.id.s);
        d = (Button) findViewById(R.id.d);
        f = (Button) findViewById(R.id.f);
        g = (Button) findViewById(R.id.g);
        h = (Button) findViewById(R.id.h);
        j = (Button) findViewById(R.id.j);
        k = (Button) findViewById(R.id.k);
        l = (Button) findViewById(R.id.l);
        z = (Button) findViewById(R.id.z);
        x = (Button) findViewById(R.id.x);
        c = (Button) findViewById(R.id.c);
        v = (Button) findViewById(R.id.v);
        b = (Button) findViewById(R.id.b);
        alt = (Button) findViewById(R.id.alt);
        shift = (Button) findViewById(R.id.shift);
        arrow_down = (Button) findViewById(R.id.arrow_down);
        enter = (Button) findViewById(R.id.arrow_enter);
        playPauseButton = findViewById(R.id.mouse_right);
        nextButton = findViewById(R.id.mouse_left);
        screen=findViewById(R.id.screen);
        // previousButton = (Button)findViewById(R.id.mousepad);

        //this activity extends View.OnClickListener, set this as onClickListener
        //for all buttons
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected && out!=null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.PLAY);
                        }
                    }).start();//send "play" to server
                }
            }
        });
        nextButton.setOnClickListener(this);
//        previousButton.setOnClickListener(this);

        //Get reference to the TextView acting as mousepad
        mousePad = (TextView)findViewById(R.id.mousepad);

        //capture finger taps and movement on the textview
        mousePad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected && out!=null){
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            //save X and Y positions when user touches the TextView
                            initX =event.getX();
                            initY =event.getY();
                            mouseMoved=false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            disX = event.getX()- initX; //Mouse movement in x direction
                            disY = event.getY()- initY; //Mouse movement in y direction
                            /*set init to new position so that continuous mouse movement
                            is captured*/
                            initX = event.getX();
                            initY = event.getY();
                            if(disX !=0|| disY !=0){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        out.println(disX +","+ disY); //send mouse movement to server
                                    }
                                }).start();
                            }
                            mouseMoved=true;
                            break;
                        case MotionEvent.ACTION_UP:
                            //consider a tap only if usr did not move mouse after ACTION_DOWN
                            if(!mouseMoved){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        out.println(Constants.MOUSE_LEFT_CLICK);
                                    }
                                }).start();
                            }
                    }
                }
                return true;
            }
        });


        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.P);
                        }
                    }).start();


                }


            }
        });


        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.Sc);
                            out.println(Constants.Sc);

                        }
                    }).start();
                    new ReceivingData(socket).start();

                }


            }
        });

        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.N);
                        }
                    }).start();


                }


            }
        });

        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.Q);
                        }
                    }).start();
                }


            }
        });

        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.W);
                        }
                    }).start();


                }


            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.E);
                        }
                    }).start();

                }


            }
        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.R);
                        }
                    }).start();

                }


            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.T);
                        }
                    }).start();

                }


            }
        });



        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.Y);
                        }
                    }).start();

                }


            }
        });

        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.U);
                        }
                    }).start();

                }


            }
        });

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.I);
                        }
                    }).start();

                }


            }
        });

        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.O);
                        }
                    }).start();

                }


            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.A);
                        }
                    }).start();

                }


            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.S);
                        }
                    }).start();

                }


            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.D);
                        }
                    }).start();

                }


            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.F);
                        }
                    }).start();

                }


            }
        });

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.G);
                        }
                    }).start();

                }


            }
        });

        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.H);
                        }
                    }).start();

                }


            }
        });

        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.J);
                        }
                    }).start();

                }


            }
        });

        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.K);
                        }
                    }).start();

                }


            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.L);
                        }
                    }).start();

                }


            }
        });

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.Z);
                        }
                    }).start();

                }


            }
        });

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.X);
                        }
                    }).start();

                }


            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.C);
                        }
                    }).start();

                }


            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.V);
                        }
                    }).start();

                }


            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.B);
                        }
                    }).start();

                }


            }
        });

        alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.ALT);
                        }
                    }).start();

                }


            }
        });

        arrow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.UP);
                        }
                    }).start();

                }


            }
        });

        arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.DOWN);
                        }
                    }).start();

                }


            }
        });

        shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.SHIFT);
                        }
                    }).start();

                }


            }
        });


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected && out != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            out.println(Constants.ENTER);
                        }
                    }).start();

                }


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_connect) {
            ConnectPhoneTask connectPhoneTask = new ConnectPhoneTask();
            connectPhoneTask.execute(Constants.SERVER_IP);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while(true) {
//                        try {
//                            try {
//                                socket.close();
//                            }catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            image.setImageBitmap(BitmapFactory.decodeStream(socket.getInputStream()));
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        } catch(Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();//try to connect to server in another thread
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //OnClick method is called when any of the buttons are pressed
    @Override
    public void onClick(final View v) {
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(isConnected && out!=null) {
            try {
                out.println("exit"); //tell server to exit
                socket.close(); //close socket
            } catch (IOException e) {
                Log.e("remotedroid", "Error in closing socket", e);
            }
        }
    }

    public class ConnectPhoneTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            try {
                InetAddress serverAddr = InetAddress.getByName(params[0]);
                socket = new Socket(serverAddr, Constants.port);//Open socket on server IP and port
            } catch (IOException e) {
                Log.e("remotedroid", "Error while connecting", e);
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            isConnected = result;
            Toast.makeText(context,isConnected?"Connected to server!":"Error while connecting",Toast.LENGTH_LONG).show();
            try {
                if(isConnected) {
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                            .getOutputStream() )), true);

                    //new ReceivingData().start();///create output stream to send data to server
                }
            }catch (IOException e){
                Log.e("remotedroid", "Error while creating OutWriter", e);
                Toast.makeText(context,"Error while connecting",Toast.LENGTH_LONG).show();
            }
        }
    }

    class ReceivingData extends Thread {

        InputStream is;
        BufferedInputStream bufferedInputStream;
        ReceivingData(Socket socket) {
            try {
                is = socket.getInputStream();
                bufferedInputStream = new BufferedInputStream(is);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(true) {
                Log.e("MY TAG, ", "I AM IN THE RUN");

                    final Drawable drawable = Drawable.createFromStream(is, "name");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Log.e("MY TAG", "HELLO "+ drawable);
                    if(drawable != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                image.setImageDrawable(drawable);
                            }
                        });

                    }else
                        break;
            }
        }
    }
}

