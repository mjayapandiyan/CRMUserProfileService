Insert into ROLE_MST (ROLE_ID,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE,ROLE_DESC) values (1000,'9999',null,null,null,'ROLE_ADMIN');
Insert into ROLE_MST (ROLE_ID,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE,ROLE_DESC) values (2000,'9999',null,null,null,'ROLE_DIRECTOR');
Insert into ROLE_MST (ROLE_ID,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE,ROLE_DESC) values (3000,'9999',null,null,null,'ROLE_EMPLOYEE');
Insert into ROLE_MST (ROLE_ID,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE,ROLE_DESC) values (4000,'9999',null,null,null,'ROLE_GUEST');



INSERT INTO company_info (
    company_id,
    company_name,
    gst_effective_date,
    gst_no,
    gst_percentage,
    registration_date,
    registration_no,
    website
) VALUES (
    1,
    'HCL TECHNOLOGIES LIMITED',
    sysdate+5,
    '09AAACH1645P4Z6',
    '100',
    '06/07/2017',
    'L74140DL1991PLC046369',
    'https://www.hcl.com'
);

Insert into PROJECTS_DETAILS (PROJECT_ID,ALLOCATION_TYPE,BILLING_AMOUNT_USD,CLIENT_ID,CLIENT_NAME,CONTRACT_END_DATE,CONTRACT_SIGNED_DATE,CREATED_AT,CREATED_BY,MODIFIED_BY,PROJECT_NAME,STATUS,UPDATED_AT)
 values (9999,'BENCH',0,1,'CRMWEB',null,null,null,null,null,'NA','ACTIVE',null);


INSERT INTO address (
    address_id,
    city,
    country,
    landmark,
    pincode,
    state
) VALUES (
    1,
    '3659',
    '101',
    'Radha nagar',
    '600044',
    '35'
);



INSERT INTO user_profile (
    profile_id,
    company_id,
    COMPANY_NAME,
    director_id,
    director_name,
    user_id,
    PROJECT_ID,
    url
) VALUES (
    100,
    1,
    'HCL TECHNOLOGIES LIMITED',
    1,
    'Kannan',
    1000,
    9999,
    'https://www.myhcl.com'
);

INSERT INTO user_details (
    user_id,
    access_code,
    contact_no,
    created_at,
    created_by,
    QUALIFICATION,
    dob,
    email_id,
    filename,
    first_name,
    gender,
    last_name,
    login_date,
    modified_by,
    password,
    role,
    role_id,
    status,
    title,
    updated_at,
    username,
    address_id,
    PROFILE_ID
) VALUES (
    1000,
    'WESFSSFS',
    '9787229364',
    NULL,
    NULL,
    'MCA',
    '01-01-2020',
    'mjayapandiyan@gmail.com',
    NULL,
    'Jayapandiyan',
    'Male',
    'Mani',
    sysdate,
    NULL,
    '$2a$10$g7WAbTBDhIFpDq6F90zwsezMHTbqcuhWwBMrQCSAPUw52UnuDzBmq',
    'Developer',
    2000,
    'Active',
    'Mr',
    NULL,
    'mjayapandiyan@gmail.com',
    1,
    100
);

Insert into CONFIG_PARAM (CONFIG_ID,CREATED_AT,CREATED_BY,MODIFIED_AT,MODIFIED_BY,PATH,STATUS,VALUE,ROLE_ID,COMPONENT) values (1,null,'9999',null,null,'/welcome','Active','Home',2000,'Menu');
Insert into CONFIG_PARAM (CONFIG_ID,CREATED_AT,CREATED_BY,MODIFIED_AT,MODIFIED_BY,PATH,STATUS,VALUE,ROLE_ID,COMPONENT) values (2,null,'9999',null,null,'/profile','Active','Profile',2000,'Menu');
Insert into CONFIG_PARAM (CONFIG_ID,CREATED_AT,CREATED_BY,MODIFIED_AT,MODIFIED_BY,PATH,STATUS,VALUE,ROLE_ID,COMPONENT) values (3,null,'9999',null,null,'/associate','Active','Manage Associates',2000,'Menu');
Insert into CONFIG_PARAM (CONFIG_ID,CREATED_AT,CREATED_BY,MODIFIED_AT,MODIFIED_BY,PATH,STATUS,VALUE,ROLE_ID,COMPONENT) values (4,null,'9999',null,null,'/tracker','Active','Tracker',2000,'Menu');
Insert into CONFIG_PARAM (CONFIG_ID,CREATED_AT,CREATED_BY,MODIFIED_AT,MODIFIED_BY,PATH,STATUS,VALUE,ROLE_ID,COMPONENT) values (5,null,'9999',null,null,'/invoices','Active','Invoices',2000,'Menu');
Insert into CONFIG_PARAM (CONFIG_ID,CREATED_AT,CREATED_BY,MODIFIED_AT,MODIFIED_BY,PATH,STATUS,VALUE,ROLE_ID,COMPONENT) values (6,null,'9999',null,null,'/reports','Active','Reports',2000,'Menu');
Insert into CONFIG_PARAM (CONFIG_ID,CREATED_AT,CREATED_BY,MODIFIED_AT,MODIFIED_BY,PATH,STATUS,VALUE,ROLE_ID,COMPONENT) values (7,null,'9999',null,null,'/logout','Active','Logout',2000,'Menu');
