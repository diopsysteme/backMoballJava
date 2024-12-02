package org.example.backmobile.Services.Impl;

import org.springframework.stereotype.Service;

@Service
public class CardHtml {
    public String generateHtmlCard(String qrCodeBase64, String name, String accountNumber, String expiryDate) {
        // Escape any % characters in the input parameters to prevent formatting issues
        String escapedName = name.replace("%", "%%");
        String escapedAccountNumber = accountNumber.replace("%", "%%");
        String escapedExpiryDate = expiryDate.replace("%", "%%");
        String escapedQrCodeBase64 = qrCodeBase64.replace("%", "%%");

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        * {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                        }
                        body {
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            min-height: 100vh;
                            background: #f0f2f5;
                            font-family: 'Arial', sans-serif;
                        }
                        
                        .card-container {
                            position: relative;
                            width: 400px;
                            height: 250px;
                            background: linear-gradient(45deg, #1a1f71, #2b3595, #4b54b9);
                            border-radius: 20px;
                            padding: 30px;
                            color: white;
                            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
                            overflow: hidden;
                            transition: all 0.3s ease;
                        }
                        
                        .card-container::before {
                            content: '';
                            position: absolute;
                            top: 0;
                            left: 0;
                            width: 100%%;
                            height: 100%%;
                            background: linear-gradient(45deg, rgba(255,255,255,0.1), rgba(255,255,255,0.05));
                            z-index: 1;
                        }
                        
                        .chip {
                            position: absolute;
                            top: 30px;
                            left: 30px;
                            width: 50px;
                            height: 40px;
                            background: linear-gradient(135deg, #ffd700, #ffaa00);
                            border-radius: 8px;
                            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                        }
                        .chip::before {
                            content: '';
                            position: absolute;
                            top: 50%%;
                            left: 50%%;
                            transform: translate(-50%%, -50%%);
                            width: 75%%;
                            height: 60%%;
                            background: linear-gradient(90deg, transparent 50%%, rgba(255,255,255,0.1) 50%%);
                            border-radius: 4px;
                        }
                        
                        .logo {
                            position: absolute;
                            top: 30px;
                            right: 30px;
                            font-size: 24px;
                            font-weight: bold;
                            letter-spacing: 2px;
                            color: rgba(255,255,255,0.9);
                        }
                        
                        .card-details {
                            margin-top: 80px;
                            z-index: 2;
                        }
                        
                        .name {
                            font-size: 20px;
                            font-weight: 500;
                            letter-spacing: 1px;
                            margin-bottom: 15px;
                            text-transform: uppercase;
                        }
                        
                        .account-number {
                            font-size: 18px;
                            letter-spacing: 2px;
                            margin-bottom: 15px;
                            color: rgba(255,255,255,0.9);
                        }
                        
                        .expiry {
                            font-size: 14px;
                            color: rgba(255,255,255,0.8);
                        }
                        
                        .card-footer {
                            position: absolute;
                            bottom: 30px;
                            right: 30px;
                            display: flex;
                            align-items: center;
                            gap: 15px;
                        }
                        
                        .qr-code {
                            width: 70px;
                            height: 70px;
                            background: white;
                            padding: 8px;
                            border-radius: 12px;
                            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                            transition: transform 0.3s ease;
                        }
                        
                        .qr-code:hover {
                            transform: scale(1.05);
                        }
                        
                        .hologram {
                            position: absolute;
                            bottom: 30px;
                            left: 30px;
                            width: 60px;
                            height: 60px;
                            background: linear-gradient(135deg, 
                                rgba(255,255,255,0.1) 0%%,
                                rgba(255,255,255,0.2) 25%%,
                                rgba(255,255,255,0.3) 50%%,
                                rgba(255,255,255,0.2) 75%%,
                                rgba(255,255,255,0.1) 100%%);
                            border-radius: 50%%;
                            animation: hologramShine 3s infinite linear;
                        }
                        
                        @keyframes hologramShine {
                            0%% { transform: rotate(0deg); }
                            100%% { transform: rotate(360deg); }
                        }
                    </style>
                </head>
                <body>
                    <div class="card-container">
                        <div class="chip"></div>
                        <div class="logo">BANK</div>
                        <div class="card-details">
                            <div class="name">%s</div>
                            <div class="account-number">%s</div>
                            <div class="expiry">Expiry: %s</div>
                        </div>
                        <div class="hologram"></div>
                        <div class="card-footer">
                            <img src="data:image/png;base64,%s" alt="QR Code" class="qr-code"/>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(escapedName, escapedAccountNumber, escapedExpiryDate, escapedQrCodeBase64);
    }
}